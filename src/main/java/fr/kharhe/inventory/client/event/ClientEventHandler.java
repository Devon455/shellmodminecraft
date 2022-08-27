package fr.kharhe.inventory.client.event;

import fr.kharhe.inventory.Main;
import fr.kharhe.inventory.common.packets.CSMessageOpenInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEventHandler {

    public ClientEventHandler() {
    }

    public static final Minecraft MC = Minecraft.getMinecraft();

    @SubscribeEvent
    public void guiOpen(GuiOpenEvent event) {
        /*if (event.getGui() != null && event.getGui() instanceof GuiMainMenu) {
            //event.setGui(new GuiMainMenuCustom());
            //System.out.println("Main Menu Load !");
        }*/
        if (event.getGui() != null && event.getGui() instanceof GuiInventory && !MC.player.isCreative()) {
            event.setCanceled(true);
            Main.network.sendToServer(new CSMessageOpenInventory((byte) 5));
            //System.out.println("Creative ? : " + MC.player.isCreative());

        }
    }
}
