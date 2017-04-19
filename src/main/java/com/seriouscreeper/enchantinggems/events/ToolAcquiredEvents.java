package com.seriouscreeper.enchantinggems.events;

import com.seriouscreeper.enchantinggems.EnchantingGems;
import com.seriouscreeper.enchantinggems.init.ModItems;
import com.seriouscreeper.enchantinggems.items.ItemGem;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;


public class ToolAcquiredEvents {
    @SubscribeEvent
    public void craftedTool(PlayerEvent.ItemCraftedEvent event) {
        ItemStack stack = event.crafting;
        Item item = event.crafting.getItem();

        if(item instanceof ItemTool || item instanceof ItemSword || item instanceof ItemBow) {
            createItemSlots(stack);
        }
    }


    private void createItemSlots(ItemStack item) {
        NBTTagCompound originalTag = item.getOrCreateSubCompound(EnchantingGems.CompoundKey);
        NBTTagList list = new NBTTagList();

        for(int i = 0; i < 3; i++) {
            NBTTagCompound slotTag = new NBTTagCompound();
            slotTag.setString(EnchantingGems.GemTypeKey, EnchantingGems.SlotTypes.ALL.toString());
            slotTag.setBoolean(EnchantingGems.ExtendedKey, false);
            list.appendTag(slotTag);
        }

        originalTag.setTag("slots", list);
    }


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
