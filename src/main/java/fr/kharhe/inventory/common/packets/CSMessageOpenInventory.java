package fr.kharhe.inventory.common.packets;

import fr.kharhe.inventory.Main;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CSMessageOpenInventory implements IMessage
{
    private byte id;

    public CSMessageOpenInventory(byte id) {
        this.id = id;
    }

    public CSMessageOpenInventory() {}

    @Override
    public void fromBytes(ByteBuf buf) {
        id = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(id);
    }

    public static class Handler implements IMessageHandler<CSMessageOpenInventory, IMessage>
    {
        @Override
        public IMessage onMessage(CSMessageOpenInventory message, MessageContext ctx) {
            if(message.id == 5){
                ctx.getServerHandler().player.openGui(Main.instance, 5, null, 0, 0, 0);
            }
            return null;
        }
    }
}
