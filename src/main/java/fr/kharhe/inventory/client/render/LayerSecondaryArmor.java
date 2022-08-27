package fr.kharhe.inventory.client.render;

import fr.kharhe.inventory.common.MainCapability;
import fr.kharhe.inventory.common.MoreArmorsCapabilityModule;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class LayerSecondaryArmor extends LayerBipedArmor {
    private RenderLivingBase<?> renderer;

    public LayerSecondaryArmor(RenderLivingBase<?> rendererIn) {
        super(rendererIn);
        this.renderer = rendererIn;
    }

    @Override
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        MainCapability cap = MainCapability.get((EntityPlayer) entitylivingbaseIn);
        MoreArmorsCapabilityModule module = cap.getModule(MoreArmorsCapabilityModule.class);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, module.getMoreArmorsInv().getStackInSlot(1));
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, module.getMoreArmorsInv().getStackInSlot(2));
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, module.getMoreArmorsInv().getStackInSlot(3));
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, module.getMoreArmorsInv().getStackInSlot(0));
    }

    private boolean isLegSlot(EntityEquipmentSlot slotIn) {
        return slotIn == EntityEquipmentSlot.LEGS;
    }

    protected void renderArmorLayer(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, ItemStack itemstack) {
        //if (itemstack.getItem() instanceof ItemArmor) {
        if (!(itemstack.getItem() instanceof ItemAir)) {
            ItemArmor itemarmor = (ItemArmor) itemstack.getItem();

            EntityEquipmentSlot slotIn = itemarmor.getEquipmentSlot();
            //if (itemarmor.getEquipmentSlot() != slotIn)
            {
                ModelBiped t = this.getModelFromSlot(slotIn);
                t = getArmorModelHook(entityLivingBaseIn, itemstack, slotIn, t);
                t.setModelAttributes(renderer.getMainModel());
                t.setLivingAnimations(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
                this.setModelSlotVisible(t, slotIn);
                boolean flag = this.isLegSlot(slotIn);
                renderer.bindTexture(this.getArmorResource(entityLivingBaseIn, itemstack, slotIn, null));
                {
                    if (itemarmor.hasOverlay(itemstack)) // Allow this for anything, not only cloth
                    {
                        int i = itemarmor.getColor(itemstack);
                        float f = (float) (i >> 16 & 255) / 255.0F;
                        float f1 = (float) (i >> 8 & 255) / 255.0F;
                        float f2 = (float) (i & 255) / 255.0F;
                        GlStateManager.color(1 * f, 1 * f1, 1 * f2, 1);
                        t.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                        renderer.bindTexture(this.getArmorResource(entityLivingBaseIn, itemstack, slotIn, "overlay"));
                    }
                    { // Non-colored
                        GlStateManager.color(1, 1, 1, 1);
                        t.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                    } // Default
                    if (itemstack.hasEffect()) {
                        renderEnchantedGlint(renderer, entityLivingBaseIn, t, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                    }
                }
            }
        }
    }
}
