package com.seriouscreeper.enchantinggems.init;


import com.seriouscreeper.enchantinggems.blocks.BlockGemInfuser;

public class ModBlocks {
    public static BlockGemInfuser gemInfuser;

    public static void init() {
        gemInfuser = new BlockGemInfuser();
    }

    public static void initRenders() {
        gemInfuser.initModel();
    }
}
