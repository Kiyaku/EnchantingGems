package com.seriouscreeper.enchantinggems.liquids;

import com.seriouscreeper.enchantinggems.init.ModFluids;
import net.minecraft.block.material.Material;


public class BlockFluidCrystalWhite extends BlockFluidCrystalBase {
    public BlockFluidCrystalWhite() {
        super(ModFluids.fluidCrystalWhite, Material.WATER, ModFluids.fluidCrystalWhite.getName() + "block");
    }
}
