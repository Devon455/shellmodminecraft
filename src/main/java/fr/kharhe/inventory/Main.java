package fr.kharhe.inventory;

import fr.kharhe.inventory.common.CommonProxy;
import fr.kharhe.inventory.common.GuiHandler;
import fr.kharhe.inventory.common.MainCapability;
import fr.kharhe.inventory.common.Registry;
import fr.kharhe.inventory.common.packets.CSMessageOpenInventory;
import fr.kharhe.inventory.lib.capability.SCMessageSyncMCapability;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main
{
    public static final String MODID = "inventorymod";
    public static final String NAME = "Inventory Mod";
    public static final String VERSION = "1.0";

    @Mod.Instance(MODID)
    public static Main instance;

    @SidedProxy(clientSide = "fr.kharhe.inventory.client.ClientProxy", serverSide = "fr.kharhe.inventory.common.CommonProxy")
    public static CommonProxy proxy;

    public static SimpleNetworkWrapper network;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        network.registerMessage(CSMessageOpenInventory.Handler.class, CSMessageOpenInventory.class, 1, Side.SERVER);
        network.registerMessage(SCMessageSyncMCapability.Handler.class, SCMessageSyncMCapability.class, 2, Side.CLIENT);
        MainCapability.register();
        proxy.preInit();
        System.out.println("preInit DANS TA GROSSE DARRONNE");
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
        System.out.println("Init DANS TA GROSSE DARRONNE");

    }
}
