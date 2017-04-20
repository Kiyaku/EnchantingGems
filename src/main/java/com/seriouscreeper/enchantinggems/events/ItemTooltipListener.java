package com.seriouscreeper.enchantinggems.events;


import com.seriouscreeper.enchantinggems.init.ModItems;
import com.seriouscreeper.enchantinggems.items.ItemGem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemTooltipListener {
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void makeTooltip(ItemTooltipEvent event) {
        if(event.getItemStack().getItem() == ModItems.itemGem) {
            ItemStack stack = event.getItemStack();
            List<String> tooltips = event.getToolTip();

            if(ItemGem.GetCurrentLevel(stack) != 0) {
                tooltips.add("Level: " + ItemGem.GetCurrentLevel(stack) + " / " + ItemGem.GetEnchantment(stack).getMaxLevel());
                tooltips.add("Charge: " + ItemGem.GetCurrentEXP(stack) + " / " + ItemGem.GetMaxEXP(stack));
                tooltips.add("Enchantment: " + ItemGem.GetEnchantment(stack).getName());
            }
        }
    }
}
