package com.seriouscreeper.enchantinggems.proxy;


import com.seriouscreeper.enchantinggems.init.ModBlocks;
import com.seriouscreeper.enchantinggems.init.ModFluids;
import com.seriouscreeper.enchantinggems.init.ModItems;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        ModItems.init();
        ModBlocks.init();
        ModFluids.preInit();

        //GameRegistry.register(BlockFluidCrystalBase.instance);
        //GameRegistry.register(new ItemBlock(BlockFluidCrystalBase.instance).setRegistryName(BlockFluidCrystalBase.instance.getRegistryName()));
    }

    public void init(FMLInitializationEvent e) {
        ModBlocks.initRenders();
    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}
