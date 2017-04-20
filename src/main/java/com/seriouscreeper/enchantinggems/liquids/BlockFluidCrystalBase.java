package com.seriouscreeper.enchantinggems.liquids;

import com.seriouscreeper.enchantinggems.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;


public class BlockFluidCrystalBase extends BlockFluidClassic {
    String name = "";

    public BlockFluidCrystalBase(Fluid fluid, Material material, String name) {
        super(fluid, material);
        this.name = name;
        setCreativeTab(CreativeTabs.MISC);
        setUnlocalizedName(Reference.MOD_ID + ":" + name);
        setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
    }
}
