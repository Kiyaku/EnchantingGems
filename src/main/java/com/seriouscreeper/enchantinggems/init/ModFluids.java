package com.seriouscreeper.enchantinggems.init;

import com.seriouscreeper.enchantinggems.Reference;
import com.seriouscreeper.enchantinggems.liquids.BlockFluidCrystalBase;
import com.seriouscreeper.enchantinggems.liquids.BlockFluidCrystalRed;
import com.seriouscreeper.enchantinggems.liquids.BlockFluidCrystalWhite;
import com.seriouscreeper.enchantinggems.liquids.FluidCrystalRed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;


public class ModFluids {
    public static Fluid fluidCrystalWhite;
    public static FluidCrystalRed fluidCrystalRed;
    public static Fluid fluidCrystalGreen;
    public static Fluid fluidCrystalBlue;

    public static BlockFluidCrystalBase fluidBlockWhite;
    public static BlockFluidCrystalBase fluidBlockRed;

    private static ResourceLocation water_still = new ResourceLocation("enchantinggems:blocks/water_still");
    private static ResourceLocation water_flow = new ResourceLocation("enchantinggems:blocks/water_flow");
    private static ModelResourceLocation fluidLocation = new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":liquidcrystalwhiteblock", "fluid");


    public static void preInit() {
        fluidCrystalWhite = new Fluid("liquidcrystalwhite", water_still, water_flow).setLuminosity(15).setDensity(100).setViscosity(1500);
        fluidCrystalRed = new FluidCrystalRed("liquidcrystalred").setLuminosity(15).setDensity(100).setViscosity(1500);
        fluidCrystalGreen = new Fluid("liquidcrystalgreen", water_still, water_flow).setLuminosity(15).setDensity(100).setViscosity(1500);
        fluidCrystalBlue = new Fluid("liquidcrystalblue", water_still, water_flow).setLuminosity(15).setDensity(100).setViscosity(1500);

        FluidRegistry.registerFluid(fluidCrystalWhite);
        FluidRegistry.registerFluid(fluidCrystalRed);
        FluidRegistry.registerFluid(fluidCrystalGreen);
        FluidRegistry.registerFluid(fluidCrystalBlue);

        fluidBlockWhite = new BlockFluidCrystalWhite();
        fluidBlockRed = new BlockFluidCrystalRed();

        GameRegistry.register(fluidBlockWhite);
        GameRegistry.register(fluidBlockRed);
        GameRegistry.register(new ItemBlock(fluidBlockWhite).setRegistryName(fluidBlockWhite.getRegistryName()));
        GameRegistry.register(new ItemBlock(fluidBlockRed).setRegistryName(fluidBlockRed.getRegistryName()));

        registerVariants(fluidBlockWhite);
        registerVariants(fluidBlockRed);
    }


    @SideOnly(Side.CLIENT)
    private static void registerVariants(BlockFluidCrystalBase block) {
        Item fluid = Item.getItemFromBlock(block);
        ModelBakery.registerItemVariants(fluid);
        ModelLoader.setCustomMeshDefinition(fluid, new ItemMeshDefinition()
        {
            public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack)
            {

                return fluidLocation;
            }
        });
        ModelLoader.setCustomStateMapper(block, new StateMapperBase()
        {
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return fluidLocation;
            }
        });
    }


    private static void registerFluid(String name, Fluid fluid) {
        fluid = new Fluid(name, water_still, water_flow).setLuminosity(15).setDensity(100).setViscosity(1500);
        FluidRegistry.registerFluid(fluid);
    }
}
