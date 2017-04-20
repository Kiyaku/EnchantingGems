package com.seriouscreeper.enchantinggems.proxy;

import com.seriouscreeper.enchantinggems.init.ModItems;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    //private static ModelResourceLocation fluidLocation = new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + BlockFluidCrystalBase.name, "fluid");

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        ModItems.registerRenders();
    }
}
