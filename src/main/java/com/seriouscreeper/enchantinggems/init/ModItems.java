package com.seriouscreeper.enchantinggems.init;


import com.seriouscreeper.enchantinggems.Reference;
import com.seriouscreeper.enchantinggems.items.ItemGem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
    public static Item itemGem;

    public static void init() {
        itemGem = new ItemGem("enchanting_gem");
        GameRegistry.register(itemGem);
    }


    public static void registerRenders() {
        registerRender(itemGem, 0);
        registerRender(itemGem, 1);
        registerRender(itemGem, 2);
    }


    private static void registerRender(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.MOD_ID + ":enchanting_gem" + meta, "inventory"));
    }
}
