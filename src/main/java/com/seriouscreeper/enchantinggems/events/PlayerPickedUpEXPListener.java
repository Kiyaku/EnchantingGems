package com.seriouscreeper.enchantinggems.events;

import com.seriouscreeper.enchantinggems.init.ModItems;
import com.seriouscreeper.enchantinggems.items.ItemGem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class PlayerPickedUpEXPListener {
    @SubscribeEvent
    public void playerPickedUpEXP(PlayerPickupXpEvent event) {
        if(event.getEntityPlayer().inventory.hasItemStack(new ItemStack(ModItems.itemGem))) {
            ItemStack stack = event.getEntityPlayer().getHeldItemOffhand();

            if(stack != ItemStack.EMPTY && stack.getItem() instanceof ItemGem && ItemGem.GetCurrentLevel(stack) > 0 && ItemGem.GetCurrentEXP(stack) < ItemGem.GetMaxEXP(stack)) {
                ItemGem.AddEXP(stack, event.getOrb().getXpValue());
                event.getEntityPlayer().xpCooldown = 2;
                event.getEntityPlayer().onItemPickup(event.getOrb(), 1);
                event.getOrb().setDead();

                event.setCanceled(true);
            }
        }
    }
}
