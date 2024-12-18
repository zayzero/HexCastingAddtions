package net.mcsweatshop.hexcastingadditions.common.hex.utils;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import net.mcsweatshop.hexcastingadditions.HexCastingAdditions;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class Utils {
    public static ResourceLocation modLoc(String loc){
        return new ResourceLocation(HexCastingAdditions.MODID, loc);
    }


    public static ActionRegistryEntry CreateRegistryEntry(HexPattern prototype, Action action){
        return new ActionRegistryEntry(prototype, action);
    }

    public static void registerOrSomething(){
//        ForgeXplatImpl.INSTANCE.getActionRegistry()
//        HexRegistries.SPECIAL_HANDLER;

    }
    public static boolean isDouble(Iota iota){
        return iota instanceof DoubleIota;
    }
    public static boolean isList(Iota iota){
        return iota instanceof ListIota;
    }
    @Nullable
    public static ListIota IotaCastList(Iota iota){
        if (isList(iota)) return (ListIota) iota;
        return null;
    }
}
