package com.seriouscreeper.enchantinggems.init;

import com.seriouscreeper.enchantinggems.Reference;
import com.seriouscreeper.enchantinggems.liquids.BlockFluidCrystalGreen;
import com.seriouscreeper.enchantinggems.liquids.BlockFluidCrystalRed;
import com.seriouscreeper.enchantinggems.liquids.FluidCrystalGreen;
import com.seriouscreeper.enchantinggems.liquids.FluidCrystalRed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;


public class ModFluids {
    public static FluidCrystalRed fluidCrystalRed;
    public static FluidCrystalGreen fluidCrystalGreen;
    public static BlockFluidCrystalRed blockFluidCrystalRed;
    public static BlockFluidCrystalGreen blockFluidCrystalGreen;


    public static void preInit() {
        FluidRegistry.registerFluid(fluidCrystalRed = new FluidCrystalRed());
        FluidRegistry.registerFluid(fluidCrystalGreen = new FluidCrystalGreen());

        GameRegistry.register(blockFluidCrystalRed = new BlockFluidCrystalRed());
        GameRegistry.register(blockFluidCrystalGreen = new BlockFluidCrystalGreen());

        GameRegistry.register(new ItemBlock(blockFluidCrystalRed).setRegistryName(blockFluidCrystalRed.getRegistryName()));
        GameRegistry.register(new ItemBlock(blockFluidCrystalGreen).setRegistryName(blockFluidCrystalGreen.getRegistryName()));

        registerRender();
    }


    @SideOnly(Side.CLIENT)
    private static void registerRender() {
        registerResourceLocations(blockFluidCrystalRed);
        registerResourceLocations(blockFluidCrystalGreen);
    }


    private static void registerResourceLocations(BlockFluidClassic b) {
        Item itemFluid = Item.getItemFromBlock(b);
        ModelBakery.registerItemVariants(itemFluid);
        ModelResourceLocation fluidLocation = new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":blockfluids", b.getFluid().getName());
        ModelLoader.setCustomMeshDefinition(itemFluid, stack -> fluidLocation);
        ModelLoader.setCustomStateMapper(b, new StateMapperBase()
        {
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return fluidLocation;
            }
        });
    }


    public static void init() {
    }
}
