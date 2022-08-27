package fr.kharhe.inventory.lib.capability;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public interface CapabilityModule {
    ResourceLocation getUniqueName();

    int id();

    void writeToNbt(NBTTagCompound tag);

    void readFromNbt(NBTTagCompound tagCompound);

    void writeToPacket(ByteBuf buf);

    void receivePacketData(EntityPlayer player, CapabilityPacketData data);

    default void tick(ModularCapability modularCapability, EntityPlayer playerIn) {
    }

    interface CapabilityPacketData {
        Class<? extends CapabilityModule> getModuleClass();
    }
}
