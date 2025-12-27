package net.mcsweatshop.hexcastingadditions.common.util;

import net.mcsweatshop.hexcastingadditions.HexCastingAdditions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemAdditions {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,HexCastingAdditions.MODID);

    public static final RegistryObject<Item> VeilTrinket = item("veiltrinket",() -> new Item(new Item.Properties().stacksTo(1)));

    public static RegistryObject<Item> item(String name, Supplier s){
        return ITEMS.register(name, s);
    }

    public static void register(IEventBus e){
        System.out.println(VeilTrinket.getId().toString());
        ITEMS.register(e);
    }
}
