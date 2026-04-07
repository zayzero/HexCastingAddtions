package net.mcsweatshop.hexcastingadditions.common.hex.stringiota;

import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.casting.mishaps.MishapBadBrainsweep;
import net.mcsweatshop.hexcastingadditions.common.hex.utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StringIota extends Iota {

    public static IotaType<StringIota> TYPE= new IotaType<StringIota>() {
        @Nullable
        @Override
        public StringIota deserialize(Tag tag, ServerLevel serverLevel) throws IllegalArgumentException {
            if (tag instanceof StringTag)
                return new StringIota(tag.getAsString());
            else return new StringIota("");
        }

        @Override
        public Component display(Tag tag) {
            return Component.literal(tag.getAsString()).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.WHITE);
        }

        @Override
        public int color() {
            return 16777215;
        }


    };
    public StringIota(@NotNull Object payload) {
        super(TYPE, payload);
    }

    public String getString(){
        if ((payload instanceof String)&&payload!=null) return(String) payload;
        return "";
    }
    @Override
    public boolean isTruthy() {
        return !getString().isEmpty();
    }

    @Override
    protected boolean toleratesOther(Iota iota) {
        return iota instanceof StringIota && ((StringIota) iota).getString().equals(getString());
    }

    @Override
    public @NotNull Tag serialize() {
        String s="";
        if (payload instanceof String) s=(String) payload;
        return StringTag.valueOf(s);
    }

    public static DoubleIota toDoubleIota(@NotNull String s) throws Utils.MishapBadStringConversion {
        Double d = Utils.toDouble(s);
        return new DoubleIota(d);
    }
}
