package net.mcsweatshop.hexcastingadditions;

import com.mojang.logging.LogUtils;
import net.mcsweatshop.hexcastingadditions.common.events.ExecuteIotasEvent;
import net.mcsweatshop.hexcastingadditions.common.events.ModEvents;
import net.mcsweatshop.hexcastingadditions.common.hex.actions.Patterns;
import net.mcsweatshop.hexcastingadditions.common.net.PacketHandler;
import net.mcsweatshop.hexcastingadditions.common.util.ItemAdditions;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import static net.mcsweatshop.hexcastingadditions.common.util.ItemAdditions.ITEMS;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(HexCastingAdditions.MODID)
public class HexCastingAdditions {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "hexcastingadditions";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

     public static final RegistryObject<CreativeModeTab> HEXCASTING_ADDITIONS_TAB = CREATIVE_MODE_TABS.register("hexcastingadditionstab", () -> CreativeModeTab.builder().withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> ItemAdditions.VeilTrinket.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(ItemAdditions.VeilTrinket.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
    }).build());

    public HexCastingAdditions() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

//        modEventBus.register(ITEMS);
        ItemAdditions.register(modEventBus);
        modEventBus.register(ExecuteIotasEvent.class);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);


        CREATIVE_MODE_TABS.register(modEventBus);


        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
//        modEventBus.addListener(this::addCreative);
//        modEventBus.addListener(PacketHandler::init);
        modEventBus.addListener((FMLCommonSetupEvent e)->{
            e.enqueueWork(Patterns::registerPatterns);
        });

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        if (Config.logDirtBlock) LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
//    private void addCreative(BuildCreativeModeTabContentsEvent event) {
//        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) event.accept();
//    }


}
