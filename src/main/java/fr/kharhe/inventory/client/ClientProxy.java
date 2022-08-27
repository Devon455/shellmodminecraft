package fr.kharhe.inventory.client;

import fr.kharhe.inventory.Main;
import fr.kharhe.inventory.client.event.ClientEventHandler;
import fr.kharhe.inventory.client.render.RenderPlayerEventHandler;
import fr.kharhe.inventory.client.render.custom.Render;
import fr.kharhe.inventory.common.CommonProxy;
import fr.kharhe.inventory.common.packets.PacketOpening;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.Sys;

public class ClientProxy extends CommonProxy
{

    @Override
    public void preInit() {
        super.preInit();
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler()); //Needed before init
        Render.Debug(9050);
        //System.out.println("TA MERE LE LOADINGGGG ===================================");
    }

    @Override
    public void init() {
        super.init();
        RenderPlayerEventHandler.init();

    }

    @Override
    public void IModels(Item item, int meta, String ID) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), ID));
    }


}
