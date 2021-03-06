package com.seriouscreeper.enchantinggems.liquids;

import com.seriouscreeper.enchantinggems.Reference;
import com.seriouscreeper.enchantinggems.init.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;


public class BlockFluidCrystalGreen extends BlockFluidClassic {
    public static final String name = "blockfluidcrystalgreen";

    public BlockFluidCrystalGreen() {
        super(ModFluids.fluidCrystalGreen, Material.WATER);
        setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, 0));
        setCreativeTab(CreativeTabs.MISC);
        setUnlocalizedName(Reference.MOD_ID + ":" + name);
        setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
    }
}
