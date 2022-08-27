package fr.kharhe.inventory.common.items;

import fr.kharhe.inventory.Main;
import fr.kharhe.inventory.client.render.custom.Render;
import fr.kharhe.inventory.common.inits.ItemInit;
import fr.kharhe.inventory.utils.interfaces.IModels;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBase extends Item implements IModels {


    public ItemBase(String name) {
        setTranslationKey(name);
        setRegistryName(name);
        //setCreativeTab(Utils.metroTab);


        ItemInit.ITEMS.add(this);
    }

    @Override
    public void IModels() {
        Main.proxy.IModels(this, 0, "inventory");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        Render.Debug(9002);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
        Render.Debug(9002);
        return super.onDroppedByPlayer(item, player);
    }

}