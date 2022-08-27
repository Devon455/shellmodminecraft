package fr.kharhe.inventory.lib.capability;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import fr.kharhe.inventory.common.MoreArmorsCapabilityModule;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * A class to register the variables to sync from server to client bullet entities <br>
 * This system is different from DataManager of vanilla mc because it adds options of interpolation, and artificial lag if the connection is not regular
 */
public class ModularCapabilityRegistry {
    private static int id = -1;
    private static final BiMap<ResourceLocation, Integer> syncVarRegistry = HashBiMap.create();
    private static final Map<Integer, Function<ByteBuf, CapabilityModule.CapabilityPacketData>> syncVars = new HashMap<>();

    /**
     * @param name     an unique identifier for this variable
     * @param variable A callable creating the instance of the variable to sync
     *                 //@param useContext A bi-function called when the truc of the entity changed, to change what is synchronized, from the given side : you should return true to sync a var, false otherwise
     * @return An unique id to identify this variable, should be used when you register the variable inside of the entity's BulletEntityNetHandler
     */
    public static int registerCapabilityModule(ResourceLocation name, Function<ByteBuf, CapabilityModule.CapabilityPacketData> variable)//, BiFunction<Side, PhysicsEntityNetHandler<?>, Boolean> useContext)
    {
        if (syncVarRegistry.containsKey(name))
            throw new IllegalArgumentException("Duplicate SyncVar " + name);
        id++;
        syncVarRegistry.put(name, id);
        syncVars.put(id, variable);
        //useContexts.put(id, useContext);
        try {
            System.out.println("Register sync var " + variable + " with id " + id + " name " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    private static int getIndex(String of, List<String> in) {
        for (int i = 0; i < in.size(); i++) {
            if (in.get(i).equals(of)) {
                return i;
            }
        }
        throw new IllegalArgumentException(of + " is not in input list " + in);
    }

    public static Map<ResourceLocation, Integer> getSyncVarRegistry() {
        return syncVarRegistry;
    }

    /**
     * Internally used to create a new variable corresponding to the given id
     */
    public static CapabilityModule.CapabilityPacketData instantiate(int id, ByteBuf buf) {
        if (!syncVars.containsKey(id))
            throw new IllegalStateException("Sync slot with id " + id + " does not exists ! Check sync var registering order...");
        try {
            return syncVars.get(id).apply(buf);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Internal variables, example to add your own variables
     */
    static {
        registerCapabilityModule(MoreArmorsCapabilityModule.ID, MoreArmorsCapabilityModule.PacketData::new);
    }
}
