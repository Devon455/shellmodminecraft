package fr.kharhe.inventory.common;

import fr.kharhe.inventory.lib.capability.ModularCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

public class MainCapability extends ModularCapability
{
	@CapabilityInject(MainCapability.class)
	public static final Capability<MainCapability> LAO_CAPA = null;

	public static MainCapability get(EntityPlayer player)
	{
		return player.getCapability(LAO_CAPA, null);
	}

	public MainCapability(EntityPlayer playerIn) throws Exception {
		super(playerIn);
		addModules(new MoreArmorsCapabilityModule(this));
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == LAO_CAPA;
	}
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == LAO_CAPA ? (T)this : null;
	}

	public static class Storage implements Capability.IStorage<MainCapability>
	{
	    @Override
	    public NBTBase writeNBT(Capability<MainCapability> capability, MainCapability instance, EnumFacing side) {return null;}

	    @Override
	    public void readNBT(Capability<MainCapability> capability, MainCapability instance, EnumFacing side, NBTBase nbt) {}

	}
	public static class Factory implements Callable<MainCapability>
	{
	    @Override
	    public MainCapability call() throws Exception {return null;}
	}
	public static void register()
	{
	    CapabilityManager.INSTANCE.register(MainCapability.class, new MainCapability.Storage(), new MainCapability.Factory());
	}
}
