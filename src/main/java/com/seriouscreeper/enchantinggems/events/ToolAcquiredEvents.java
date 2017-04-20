package com.seriouscreeper.enchantinggems.events;

import com.seriouscreeper.enchantinggems.helper.SocketsNBTHelper;
import net.minecraft.item.*;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;


public class ToolAcquiredEvents {
    @SubscribeEvent
    public void pickedUpTool(EntityItemPickupEvent event) {
        ItemStack stack = event.getItem().getEntityItem();
        createItemSlots(stack);
    }


    @SubscribeEvent
    public void craftedTool(PlayerEvent.ItemCraftedEvent event) {
        ItemStack stack = event.crafting;
        createItemSlots(stack);
    }


    private void createItemSlots(ItemStack stack) {
        Item item = stack.getItem();

        if(item instanceof ItemTool || item instanceof ItemSword || item instanceof ItemBow) {
            SocketsNBTHelper.CreateSockets(stack);
        }
    }
}
