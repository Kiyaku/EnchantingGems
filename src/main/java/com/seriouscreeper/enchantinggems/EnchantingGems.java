package com.seriouscreeper.enchantinggems;

import com.seriouscreeper.enchantinggems.events.ItemTooltipListener;
import com.seriouscreeper.enchantinggems.events.PlayerPickedUpEXPListener;
import com.seriouscreeper.enchantinggems.events.ToolAcquiredEvents;
import com.seriouscreeper.enchantinggems.init.ModFluids;
import com.seriouscreeper.enchantinggems.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class EnchantingGems {
    @Mod.Instance
    public static EnchantingGems instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;


    public final static String CompoundKey = "enchanting_gems";
    public final static String GemTypeKey = "gemType";
    public final static String ExtendedKey = "isExtended";
    public final static String SlottedGemKey = "slottedGem";
    public enum SlotTypes {
        ALL,
        AMETHYST,
        RUBY,
        TOPAZ
    }
    public enum GemSizes {
        CHIPPED,
        REGULAR,
        FLAWLESS,
        PERFECT
    }


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);

        ToolAcquiredEvents toolAcquiredEvents = new ToolAcquiredEvents();
        MinecraftForge.EVENT_BUS.register(toolAcquiredEvents);

        PlayerPickedUpEXPListener playerPickedUpEXPListener = new PlayerPickedUpEXPListener();
        MinecraftForge.EVENT_BUS.register(playerPickedUpEXPListener);

        ItemTooltipListener itemTooltipListener = new ItemTooltipListener();
        MinecraftForge.EVENT_BUS.register(itemTooltipListener);
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
