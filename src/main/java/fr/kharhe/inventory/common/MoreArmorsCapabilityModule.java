package fr.kharhe.inventory.common;

import fr.kharhe.inventory.Main;
import fr.kharhe.inventory.lib.capability.CapabilityModule;
import fr.kharhe.inventory.lib.capability.ModularCapabilityRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class MoreArmorsCapabilityModule implements CapabilityModule {
    public static final ResourceLocation ID = new ResourceLocation(Main.MODID, "morearmors_cap");

    private final InventoryBasic moreArmorsInv = new InventoryBasic("morearmors.inv", false, 4);

    public MoreArmorsCapabilityModule(MainCapability mainCapability) {

    }

    public InventoryBasic getMoreArmorsInv() {
        return moreArmorsInv;
    }

    @Override
    public ResourceLocation getUniqueName() {
        return ID;
    }

    @Override
    public int id() {
        return ModularCapabilityRegistry.getSyncVarRegistry().get(getUniqueName());
    }

    @Override
    public void writeToNbt(NBTTagCompound tag) {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < moreArmorsInv.getSizeInventory(); i++) {
            list.appendTag(moreArmorsInv.getStackInSlot(i).serializeNBT());
        }
        tag.setTag("ma_inv", list);
    }

    @Override
    public void readFromNbt(NBTTagCompound tagCompound) {
        NBTTagList list = tagCompound.getTagList("ma_inv", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < list.tagCount(); i++) {
            moreArmorsInv.setInventorySlotContents(i, new ItemStack(list.getCompoundTagAt(i)));
        }
    }

    @Override
    public void writeToPacket(ByteBuf buf) {
        buf.writeInt(moreArmorsInv.getSizeInventory());
        for (int i = 0; i < moreArmorsInv.getSizeInventory(); i++) {
            ByteBufUtils.writeItemStack(buf, moreArmorsInv.getStackInSlot(i));
        }
    }

    @Override
    public void receivePacketData(EntityPlayer player, CapabilityPacketData data) {
        for (int i = 0; i < moreArmorsInv.getSizeInventory(); i++) {
            moreArmorsInv.setInventorySlotContents(i, ((PacketData) data).stacks[i]);
        }
    }

    public static class PacketData implements CapabilityPacketData {
        private final ItemStack[] stacks;

        public PacketData(ByteBuf buf) {
            stacks = new ItemStack[buf.readInt()];
            for (int i = 0; i < stacks.length; i++) {
                stacks[i] = ByteBufUtils.readItemStack(buf);
            }
        }

        @Override
        public Class<? extends CapabilityModule> getModuleClass() {
            return MoreArmorsCapabilityModule.class;
        }

    }
}
