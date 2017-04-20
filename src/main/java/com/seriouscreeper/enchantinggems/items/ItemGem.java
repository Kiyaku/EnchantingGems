package com.seriouscreeper.enchantinggems.items;

import com.seriouscreeper.enchantinggems.EnchantingGems;
import com.seriouscreeper.enchantinggems.helper.SocketsNBTHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ItemGem extends Item {
    private enum Quality {
        DULL,
        REGULAR,
        SHARPENED,
        FLAWLESS,
        PERFECT,
        LEGENDARY
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


    private Random rand = new Random();


    public ItemGem(String unlocalizedName) {
        super();
        this.setHasSubtypes(true);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(unlocalizedName);
        this.setMaxStackSize(1);
        setCreativeTab(CreativeTabs.MISC);
    }


    private void AssignColor(ItemStack stack) {
        stack.getOrCreateSubCompound("Data").setInteger("color", rand.nextInt(Color.values().length));
    }


    public static boolean IsValidItem(Item item) {
        return (item instanceof ItemTool || item instanceof ItemSword || item instanceof ItemBow || item instanceof ItemArmor || item instanceof ItemShield);
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


    public static boolean CanLevelUp(ItemStack stack) {
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

            if(offhandStack.getItem() instanceof ItemTool && offhandStack.getSubCompound(SocketsNBTHelper.compoundKey) != null) {
                if(CanLevelUp(stack)) {
                    if (SocketsNBTHelper.SocketGem(offhandStack, stack)) {
                    } else {
                        playerIn.sendStatusMessage(new TextComponentTranslation("enchantinggems.gems.nosockets"), true);
                    }
                } else {
                    playerIn.sendStatusMessage(new TextComponentTranslation("enchantinggems.gems.notcharged"), true);
                }
            } else {
                if (GetCurrentLevel(stack) == 0) {
                    // TEMP, replace with an infusion later
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
