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
            ItemStack gemStack = event.getEntityPlayer().inventory.getStackInSlot(event.getEntityPlayer().inventory.getSlotFor(new ItemStack(ModItems.itemGem)));
            ItemGem gem = ((ItemGem)gemStack.getItem());

            if(gem.GetCurrentLevel() > 0 && gem.GetCurrentEXP() < gem.GetMaxEXP()) {
                gem.AddEXP(event.getOrb().getXpValue());
                event.getEntityPlayer().xpCooldown = 2;
                event.getEntityPlayer().onItemPickup(event.getOrb(), 1);
                event.getOrb().setDead();

                event.setCanceled(true);
            }
        }
    }
}
