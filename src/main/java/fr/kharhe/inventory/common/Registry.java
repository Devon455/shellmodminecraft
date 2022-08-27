package fr.kharhe.inventory.common;

import fr.kharhe.inventory.Main;
import fr.kharhe.inventory.common.inits.ItemInit;
import fr.kharhe.inventory.utils.interfaces.IModels;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.List;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class Registry
{
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> regItems) {
        System.out.println("REGISTER ITEM DANS LE FIAK A TA DARONNE");
        regItems.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void ModelsRegister(ModelRegistryEvent regModels) {
        for (Item item : ItemInit.ITEMS) {
            if (item instanceof IModels) {
                ((IModels) item).IModels();
            }
        }
    }


}
