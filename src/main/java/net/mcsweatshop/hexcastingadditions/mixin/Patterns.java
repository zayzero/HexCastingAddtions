package net.mcsweatshop.hexcastingadditions.mixin;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import net.mcsweatshop.hexcastingadditions.HexCastingAdditions;
import net.mcsweatshop.hexcastingadditions.common.hex.patterns.*;
import net.mcsweatshop.hexcastingadditions.common.util.ItemAdditions;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

import static at.petrak.hexcasting.common.lib.hex.HexActions.make;

@Mixin(HexActions.class)
public abstract class Patterns{


    @Shadow @Final private static Map<ResourceLocation, ActionRegistryEntry> ACTIONS;

    private static ActionRegistryEntry makes(String name, ActionRegistryEntry are) {
        ActionRegistryEntry old = (ActionRegistryEntry)ACTIONS.put(new ResourceLocation(HexCastingAdditions.MODID,name), are);
        if (old != null) {
            throw new IllegalArgumentException("Typo? Duplicate id " + name);
        } else {

            return are;
        }
    }

    @Unique
    private static final ActionRegistryEntry CHAT = makes("chat", new ActionRegistryEntry(HexPattern.fromAngles("wdwww", HexDir.EAST), Chat.INSTANCE));
    private static final ActionRegistryEntry TOSTRING = makes("tostring", new ActionRegistryEntry(HexPattern.fromAngles("aed", HexDir.WEST), ToString.INSTANCE));
    private static final ActionRegistryEntry READSTRING = makes("readstring", new ActionRegistryEntry(HexPattern.fromAngles("aede", HexDir.WEST), OpReadItem.INSTANCE));
    private static final ActionRegistryEntry SUBSTRING = makes("substring", new ActionRegistryEntry(HexPattern.fromAngles("aq", HexDir.WEST), OpSubString.INSTANCE));
    private static final ActionRegistryEntry CONCATSTRING = makes("concatstring", new ActionRegistryEntry(HexPattern.fromAngles("waawe", HexDir.NORTH_EAST), OpConcatString.INSTANCE));
    private static final ActionRegistryEntry STRINGTODOUBLE = makes("stringtodouble", new ActionRegistryEntry(HexPattern.fromAngles("aedq", HexDir.NORTH_EAST), OpStringToFloat.INSTANCE));
    private static final ActionRegistryEntry STRINGLEN = makes("stringlen", new ActionRegistryEntry(HexPattern.fromAngles("aqaa", HexDir.NORTH_WEST), OpStringLen.INSTANCE));
    private static final ActionRegistryEntry EvalAsynch = makes("asynch", new ActionRegistryEntry(HexPattern.fromAngles("wdwwd", HexDir.EAST), OpEvalAsynch.INSTANCE));
}
