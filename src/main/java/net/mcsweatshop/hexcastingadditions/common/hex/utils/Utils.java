package net.mcsweatshop.hexcastingadditions.common.hex.utils;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import net.mcsweatshop.hexcastingadditions.HexCastingAdditions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

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


    public static Double toDouble(String s) throws MishapBadStringConversion {
        try {
            return Double.parseDouble(s);
        } catch (Exception e){
            throw new MishapBadStringConversion(s);
        }
    }

    public static class MishapBadStringConversion extends Mishap{

        public final String issue;

        public MishapBadStringConversion(String issue){
            this.issue = issue;
        }

        @NotNull
        @Override
        public FrozenPigment accentColor(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context) {
            return dyeColor(DyeColor.YELLOW);
        }

        @org.jetbrains.annotations.Nullable
        @Override
        protected Component errorMessage(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context) {
            return error("unparsablenumber", issue);
        }

        @Override
        public void execute(@NotNull CastingEnvironment castingEnvironment, @NotNull Mishap.Context context, @NotNull List<Iota> list) {

        }
    }

    public static void println(String str){
        System.out.println(str);
    }
}
