package fr.kharhe.inventory.lib.capability;

import fr.kharhe.inventory.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.Map;

public abstract class ModularCapability implements ICapabilityProvider, INBTSerializable<NBTTagCompound>
{
	private final EntityPlayer playerIn;
	private final Map<Class<? extends CapabilityModule>, CapabilityModule> modules = new HashMap<>();

	public ModularCapability(EntityPlayer playerIn) {
		this.playerIn = playerIn;
	}

	protected void addModules(CapabilityModule... modules) {
		for(CapabilityModule m : modules) {
			this.modules.put(m.getClass(), m);
		}
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		for(CapabilityModule m : modules.values()) {
			NBTTagCompound tag = new NBTTagCompound();
			m.writeToNbt(tag);
			compound.setTag(m.getUniqueName().toString(), tag);
		}
		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound compound) {
		for(CapabilityModule m : modules.values()) {
			NBTTagCompound tag = compound.getCompoundTag(m.getUniqueName().toString());
			m.readFromNbt(tag);
		}
	}

	public void sync(Class<? extends CapabilityModule>... modules) {
		CapabilityModule[] instances = new CapabilityModule[modules.length];
		for (int i = 0; i < modules.length; i++) {
			instances[i] = this.modules.get(modules[i]);
		}
		sync(instances);
	}

	public void sync(CapabilityModule... modules) {
		if(!playerIn.world.isRemote) {
			Main.network.sendToAll(getSyncPacket(modules));
		}
	}

	public SCMessageSyncMCapability getSyncPacket(CapabilityModule... modules) {
		return new SCMessageSyncMCapability(playerIn.getEntityId(), modules);
	}

	public Map<Class<? extends CapabilityModule>, CapabilityModule> getModules() {
		return modules;
	}

	public void tick()
	{
		for(CapabilityModule m : modules.values()) {
			m.tick(this, playerIn);
		}
	}

	public <T extends CapabilityModule> T getModule(Class<T> clazz) {
		return (T) modules.get(clazz);
	}
}
