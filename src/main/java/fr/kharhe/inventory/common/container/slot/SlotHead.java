package fr.kharhe.inventory.common.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class SlotHead extends Slot {
    public SlotHead(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
        //RenderPlayerEvent.SetArmorModel
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return true;
    }
}
