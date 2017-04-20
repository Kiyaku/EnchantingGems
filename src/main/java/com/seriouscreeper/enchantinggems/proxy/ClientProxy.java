package com.seriouscreeper.enchantinggems.proxy;

import com.seriouscreeper.enchantinggems.Reference;
import com.seriouscreeper.enchantinggems.init.ModItems;
import com.seriouscreeper.enchantinggems.liquids.BlockFluidCrystalBase;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    //private static ModelResourceLocation fluidLocation = new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + BlockFluidCrystalBase.name, "fluid");

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        ModItems.registerRenders();
/*
        Item fluid = Item.getItemFromBlock(BlockFluidCrystalBase.instance);
        ModelBakery.registerItemVariants(fluid);
        ModelLoader.setCustomMeshDefinition(fluid, new ItemMeshDefinition()
        {
            public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack)
            {
                return fluidLocation;
            }
        });
        ModelLoader.setCustomStateMapper(BlockFluidCrystalBase.instance, new StateMapperBase()
        {
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return fluidLocation;
            }
        });
        */
    }
}
