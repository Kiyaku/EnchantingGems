package com.seriouscreeper.enchantinggems.helper;


import com.seriouscreeper.enchantinggems.EnchantingGems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

public class SocketsNBTHelper {
    public static final String compoundKey = "EnchantingGemsData";
    private static final String gemKey = "gem";
    private static final String extendedKey = "isExtended";
    private static Random rand = new Random();

    public static void CreateSockets(ItemStack stack) {
        NBTTagCompound originalTag = stack.getOrCreateSubCompound(compoundKey);
        if(!originalTag.hasKey("slots")) {
            NBTTagList list = new NBTTagList();

            for (int i = 0; i < rand.nextInt(2) + 1; i++) {
                NBTTagCompound slotTag = new NBTTagCompound();
                slotTag.setBoolean(extendedKey, false);
                list.appendTag(slotTag);
            }

            originalTag.setTag("slots", list);
            stack.setTagCompound(originalTag);
        }
    }


    public static boolean HasFreeSocket(ItemStack stack) {
        NBTTagCompound originalTag = stack.getOrCreateSubCompound(compoundKey);
        NBTTagList list = originalTag.getTagList("slots", Constants.NBT.TAG_COMPOUND);

        for(int i = 0; i < list.tagCount(); i++) {
            if(!list.getCompoundTagAt(i).hasKey(gemKey))
                return true;
        }

        return false;
    }


    public static NBTTagCompound GetNextFreeSocket(ItemStack stack) {
        NBTTagCompound originalTag = stack.getOrCreateSubCompound(compoundKey);
        NBTTagList list = originalTag.getTagList("slots", Constants.NBT.TAG_COMPOUND);

        System.out.println(list.tagCount());

        for(int i = 0; i < list.tagCount(); i++) {
            if(!list.getCompoundTagAt(i).hasKey(gemKey))
                return list.getCompoundTagAt(i);
        }

        return null;
    }


    public static boolean SocketGem(ItemStack tool, ItemStack gem) {
        NBTTagCompound originalTag = tool.getOrCreateSubCompound(compoundKey);
        NBTTagList list = originalTag.getTagList("slots", Constants.NBT.TAG_COMPOUND);

        for(int i = 0; i < list.tagCount(); i++) {
            if(!list.getCompoundTagAt(i).hasKey(gemKey)) {
                list.getCompoundTagAt(i).setTag(gemKey, gem.serializeNBT());
                return true;
            }
        }

        return false;
    }


    public static NBTTagList GetSockets(ItemStack stack) {
        NBTTagCompound originalTag = stack.getOrCreateSubCompound(compoundKey);
        return originalTag.getTagList("slots", Constants.NBT.TAG_COMPOUND);
    }
}
