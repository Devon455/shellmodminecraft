package fr.kharhe.inventory.common;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {


    public void preInit() {
        MinecraftForge.EVENT_BUS.register(new CapabilityEventHandler());
        //MinecraftForge.EVENT_BUS.register(new CommonEventHandler());

    }

    public void init(){}

    public void postInits(){}

    public void serverSideStart(){}

    public void serverSideStop(){}

    public void IModels(Item item, int meta, String ID) {}

    public ResourceLocation getTexture(String texture){
        return null;
    }

    public void initTextures(){}
}
