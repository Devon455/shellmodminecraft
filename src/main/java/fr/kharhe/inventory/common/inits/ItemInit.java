package fr.kharhe.inventory.common.inits;

import fr.kharhe.inventory.common.items.ItemBase;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class ItemInit {

    public static final List<Item> ITEMS = new ArrayList<>();

    public static final Item SHURIKEN = new ItemBase("shuriken");
}
