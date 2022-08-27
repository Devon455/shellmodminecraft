package fr.kharhe.inventory.lib.capability;

import fr.kharhe.inventory.common.MainCapability;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

public class SCMessageSyncMCapability implements IMessage {
    private int id;
    private CapabilityModule[] moduleData;
    private CapabilityModule.CapabilityPacketData[] receivedData;

    public SCMessageSyncMCapability() {
    }

    public SCMessageSyncMCapability(int entityId, CapabilityModule[] moduleData) {
        this.id = entityId;
        this.moduleData = moduleData;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        id = buf.readInt();
        int size = buf.readInt();
        receivedData = new CapabilityModule.CapabilityPacketData[size];
        for (int i = 0; i < size; i++) {
            int id = buf.readInt();
            System.out.println("Read module with id "+id);
            receivedData[i] = ModularCapabilityRegistry.instantiate(id, buf);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(id);
        buf.writeInt(moduleData.length);
        for (CapabilityModule module : moduleData) {
            buf.writeInt(module.id());
            //System.out.println("Save "+module+" with id "+module.id());
            module.writeToPacket(buf);
        }
    }

    public static class Handler implements IMessageHandler<SCMessageSyncMCapability, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(SCMessageSyncMCapability message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Entity p = Minecraft.getMinecraft().world.getEntityByID(message.id);
                System.out.println("Rcv sync packet "+message.id+" "+p+" "+ Arrays.toString(message.receivedData));
                if (p instanceof EntityPlayer) {
                    ModularCapability cap = MainCapability.get((EntityPlayer) p);
                    for (CapabilityModule.CapabilityPacketData data : message.receivedData) {
                        cap.getModules().get(data.getModuleClass()).receivePacketData((EntityPlayer) p, data);
                    }
                }
            });
            return null;
        }
    }
}
