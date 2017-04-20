package com.seriouscreeper.enchantinggems.liquids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;


public class FluidCrystalRed extends Fluid {
    public FluidCrystalRed(String name) {
        super(name, new ResourceLocation("enchantinggems:blocks/water_still"), new ResourceLocation("enchantinggems:blocks/water_flow"));
    }

    @Override
    public int getColor()
    {
        return 0xFFFF0000;
    }
}
