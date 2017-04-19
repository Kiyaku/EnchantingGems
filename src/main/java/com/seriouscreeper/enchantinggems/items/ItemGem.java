package com.seriouscreeper.enchantinggems.items;

import com.seriouscreeper.enchantinggems.EnchantingGems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentMending;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class ItemGem extends Item {
    private enum Quality {
        DULL,
        REGULAR,
        SHARPENED,
        FLAWLESS,
        PERFECT
    }

    private enum Color {
        WHITE("White", 0xFFFFFF),
        RED("Red", 0xFF0000),
        GREEN("Green", 0x00FF00),
        BLUE("Blue", 0x0000FF);

        private String name;
        private int hexColor;

        Color(String name, int hexColor) {
            this.name = name;
            this.hexColor = hexColor;
        }

        public String GetName() {
            return name;
        }

        public int GetColor() {
            return hexColor;
        }
    }

    public Quality GemQuality = Quality.DULL;
    public Color GemColor = Color.WHITE;
    private int currentEXP = 0;
    private int maxEXP = 100;
    private int currentLevel = 1;
    private Enchantment assignedEnchantment;


    public ItemGem(String unlocalizedName) {
        super();
        this.setHasSubtypes(true);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(unlocalizedName);
        setCreativeTab(CreativeTabs.MISC);
    }


    public int GetCurrentEXP() {
        return currentEXP;
    }


    public int GetMaxEXP() {
        return currentLevel * 100;
    }


    public int GetCurrentLevel() {
        return currentLevel;
    }


    public void AddEXP(int exp) {
        currentEXP += exp;

        if(currentEXP > maxEXP) {
            currentEXP = maxEXP;
        }

        System.out.println(currentEXP);
    }


    @Override
    public boolean hasEffect(ItemStack stack) {
        return currentLevel > 0 && currentEXP == maxEXP;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "_" + stack.getMetadata();
    }


    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for(int i = 1; i < EnchantingGems.GemSizes.values().length; i++) {
            subItems.add(new ItemStack(itemIn, 1, i - 1));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(!worldIn.isRemote && handIn != EnumHand.OFF_HAND) {
            ItemStack offhandStack = playerIn.getHeldItemOffhand();

            if (offhandStack != ItemStack.EMPTY) {
                Item offhandItem = offhandStack.getItem();

                if (offhandItem instanceof ItemTool) {
                    offhandStack.addEnchantment(Enchantments.MENDING, 1);
                    playerIn.inventory.decrStackSize(playerIn.inventory.currentItem, 1);
                }
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
