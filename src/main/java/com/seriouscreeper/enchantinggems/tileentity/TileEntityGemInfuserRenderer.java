package com.seriouscreeper.enchantinggems.tileentity;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class TileEntityGemInfuserRenderer extends TileEntitySpecialRenderer<TileEntityGemInfuser> {
    Random random = new Random();

    public TileEntityGemInfuserRenderer() {
        super();
    }

    @Override
    public void renderTileEntityAt(TileEntityGemInfuser te, double x, double y, double z, float partialTicks, int destroyStage) {
        if(te.GetItemCount() == 0)
            return;

        random.setSeed(te.getWorld().getSeed());
        float angle = 360 / te.GetItemCount();

        for(int i = 0; i < te.inventory.getSlots(); i++) {
            if(te.inventory.getStackInSlot(i) != ItemStack.EMPTY) {
                GL11.glPushMatrix();
                EntityItem item = new EntityItem(Minecraft.getMinecraft().world, x, y, z, new ItemStack(te.inventory.getStackInSlot(i).getItem()));
                item.hoverStart = te.hoverValue;
                GL11.glTranslated(x + 0.5f, y + 1.0f, z + 0.5f);
                GL11.glRotated(angle * i + (te.rotation * 50), 0, 1, 0);
                GL11.glTranslated(0.3f, 0, 0.3f);
                Minecraft.getMinecraft().getRenderManager().doRenderEntity(item, 0, 0, 0, 0, 0, true);
                GL11.glPopMatrix();
            }
        }

        te.hoverValue += 0.02;
        te.rotation += 0.01;
    }
}
