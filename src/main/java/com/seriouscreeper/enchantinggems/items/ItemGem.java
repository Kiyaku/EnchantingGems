package com.seriouscreeper.enchantinggems.items;

import com.seriouscreeper.enchantinggems.EnchantingGems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;


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
    private Random rand = new Random();


    public ItemGem(String unlocalizedName) {
        super();
        this.setHasSubtypes(true);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(unlocalizedName);
        setCreativeTab(CreativeTabs.MISC);
    }


    public static int GetCurrentEXP(ItemStack stack) {
        return stack.getOrCreateSubCompound("Data").getInteger("currentEXP");
    }


    public static int GetMaxEXP(ItemStack stack) {
        return GetCurrentLevel(stack) * 100;
    }


    public static int GetCurrentLevel(ItemStack stack) {
        return stack.getOrCreateSubCompound("Data").getInteger("currentLevel");
    }


    public static void SetLevel(ItemStack stack, int newLevel) {
        stack.getOrCreateSubCompound("Data").setInteger("currentLevel", newLevel);
    }


    public static Enchantment GetEnchantment(ItemStack stack) {
        return Enchantment.getEnchantmentByLocation(stack.getOrCreateSubCompound("Data").getString("assignedEnchantment"));
    }


    public static void SetEnchantment(ItemStack stack, Enchantment enchantment) {
        stack.getOrCreateSubCompound("Data").setString("assignedEnchantment", enchantment.getRegistryName().toString());
    }


    public boolean CanLevelUp(ItemStack stack) {
        return GetCurrentEXP(stack) >= GetMaxEXP(stack);
    }


    public static void AddEXP(ItemStack stack, int exp) {
        int currentEXP = GetCurrentEXP(stack);
        currentEXP += exp;

        if(currentEXP > GetMaxEXP(stack)) {
            currentEXP = GetMaxEXP(stack);
        }

        SetEXP(stack, currentEXP);
    }


    private static void SetEXP(ItemStack stack, int newValue) {
        stack.getOrCreateSubCompound("Data").setInteger("currentEXP", newValue);
    }


    @Override
    public boolean hasEffect(ItemStack stack) {
        return GetCurrentLevel(stack) > 0 && GetCurrentEXP(stack) == GetMaxEXP(stack);
    }


    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "_" + stack.getMetadata();
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for(int i = 1; i < EnchantingGems.GemSizes.values().length; i++) {
            subItems.add(new ItemStack(itemIn, 1, i - 1));
        }
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(!worldIn.isRemote && handIn != EnumHand.OFF_HAND) {
            ItemStack stack = playerIn.getHeldItemMainhand();
            ItemStack offhandStack = playerIn.getHeldItemOffhand();

            if(offhandStack.getItem() instanceof ItemTool && CanLevelUp(stack)) {
                offhandStack.addEnchantment(GetEnchantment(stack), GetCurrentLevel(stack));
                playerIn.inventory.decrStackSize(playerIn.inventory.currentItem, 1);
            } else {
                if (GetCurrentLevel(stack) == 0) {
                    List<EnchantmentData> enchantments = EnchantmentHelper.buildEnchantmentList(rand, new ItemStack(Items.BOOK), 50, true);
                    Enchantment enchantment = enchantments.get(rand.nextInt(enchantments.size())).enchantmentobj;

                    SetEnchantment(stack, enchantment);
                    SetLevel(stack, GetCurrentLevel(stack) + 1);
                } else if (GetEnchantment(stack) != null && CanLevelUp(stack) && GetCurrentLevel(stack) < GetEnchantment(stack).getMaxLevel()) {
                    SetLevel(stack, GetCurrentLevel(stack) + 1);
                    SetEXP(stack, 0);
                }
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
