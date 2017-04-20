package com.seriouscreeper.enchantinggems.liquids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;


public class FluidCrystalGreen extends Fluid {
    public static final String name = "fluidcrystalgreen";

    public FluidCrystalGreen() {
        super(name, new ResourceLocation("enchantinggems:blocks/water_still"), new ResourceLocation("enchantinggems:blocks/water_flow"));

    }

    @Override
    public int getColor()
    {
        return 0xFF00FF00;
    }
}
