package fr.kharhe.inventory.common;

import fr.kharhe.inventory.client.gui.GuiInv;
import fr.kharhe.inventory.client.render.custom.Render;
import fr.kharhe.inventory.common.container.ContainerGuiInv;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {


    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        Render.Debug(9000);
        System.out.println("Ton grand père le chameau execution");
        if (ID == 5) {
            return new ContainerGuiInv(player.inventory, player);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        System.out.println("getClientGuiElementExecute !");
        if (ID == 5) {
            return new GuiInv(player, player.inventory);
        }
        return null;
    }
}
