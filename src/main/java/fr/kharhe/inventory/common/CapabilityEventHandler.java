package fr.kharhe.inventory.common;

import fr.kharhe.inventory.Main;
import fr.kharhe.inventory.lib.capability.CapabilityModule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityEventHandler
{
    public static ThreadLocal<EntityLivingBase> updatingEntity = new ThreadLocal<>();

    @SubscribeEvent
    public void livingUpdate(LivingEvent.LivingUpdateEvent event) {
        updatingEntity.set(event.getEntityLiving());
    }

    @SubscribeEvent
    public void onEntityConstructing(AttachCapabilitiesEvent<Entity> event) throws Exception {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(Main.MODID + ":LAO_CAPA"), new MainCapability((EntityPlayer) event.getObject()));
        }
    }


    @SubscribeEvent
    public void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof EntityPlayer) {
            //TODO DELAY ?LaoCapability.get((EntityPlayer) event.getTarget()).sync((byte) 10);
            MainCapability capability = MainCapability.get(event.getEntityPlayer());
            capability.sync(capability.getModules().values().toArray(new CapabilityModule[0]));
        }
    }

    @SubscribeEvent
    public void clone(PlayerEvent.Clone event) {
        MainCapability old = MainCapability.get(event.getOriginal());
        MainCapability nw = MainCapability.get(event.getEntityPlayer());
    }
}
