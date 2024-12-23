package net.mcsweatshop.hexcastingadditions.common.hex.utils;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.circles.BlockEntityAbstractImpetus;
import at.petrak.hexcasting.api.casting.circles.CircleExecutionState;
import at.petrak.hexcasting.api.casting.eval.env.CircleCastEnv;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import net.mcsweatshop.hexcastingadditions.HexCastingAdditions;
import net.mcsweatshop.hexcastingadditions.mixininterfaces.AsyncRuns;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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


    public static void println(String str){
        System.out.println(str);
    }
}
