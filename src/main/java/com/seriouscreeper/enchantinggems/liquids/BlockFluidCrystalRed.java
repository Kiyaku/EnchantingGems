package com.seriouscreeper.enchantinggems.liquids;

import com.seriouscreeper.enchantinggems.init.ModFluids;
import net.minecraft.block.material.Material;


public class BlockFluidCrystalRed extends BlockFluidCrystalBase {
    public BlockFluidCrystalRed() {
        super(ModFluids.fluidCrystalRed, Material.WATER, ModFluids.fluidCrystalRed.getName() + "block");
    }
}
