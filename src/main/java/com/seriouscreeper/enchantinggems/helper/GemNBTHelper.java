package com.seriouscreeper.enchantinggems.helper;


import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GemNBTHelper {
    public int GetCurrentLevel(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();

        if(tag.hasKey("currentLevel"))
            return tag.getInteger("currentLevel");

        return 0;
    }
}
