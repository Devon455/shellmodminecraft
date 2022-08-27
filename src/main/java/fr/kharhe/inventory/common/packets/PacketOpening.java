package fr.kharhe.inventory.common.packets;

import fr.kharhe.inventory.Main;
import fr.kharhe.inventory.client.render.custom.Render;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOpening implements IMessage
{

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<CSMessageOpenInventory, IMessage>
    {
        @Override
        public IMessage onMessage(CSMessageOpenInventory message, MessageContext ctx) {
            Render.Debug(9005);
            return null;
        }
    }
}
