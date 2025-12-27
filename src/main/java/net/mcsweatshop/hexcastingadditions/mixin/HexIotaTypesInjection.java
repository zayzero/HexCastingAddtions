package net.mcsweatshop.hexcastingadditions.mixin;

import at.petrak.hexcasting.api.HexAPI;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes;
import net.mcsweatshop.hexcastingadditions.HexCastingAdditions;
import net.mcsweatshop.hexcastingadditions.common.hex.stringiota.StringIota;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(HexIotaTypes.class)
public class HexIotaTypesInjection {
    @Shadow @Final private static Map<ResourceLocation, IotaType<?>> TYPES;

    private static <U extends Iota, T extends IotaType<U>> T typed(String name, T type) {
        IotaType<?> old = (IotaType)TYPES.put(new ResourceLocation(HexCastingAdditions.MODID,name), type);
        if (old != null) {
            throw new IllegalArgumentException("Typo? Duplicate id " + name);
        } else {
            return type;
        }
    }
    private static final IotaType<StringIota> String=typed("string",StringIota.TYPE);
//

}
