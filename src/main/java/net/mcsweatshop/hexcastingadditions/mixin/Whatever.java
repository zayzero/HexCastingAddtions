package net.mcsweatshop.hexcastingadditions.mixin;

import at.petrak.hexcasting.api.HexAPI;
import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import com.electronwill.nightconfig.core.conversion.ConversionTable;
import net.mcsweatshop.hexcastingadditions.HexCastingAdditions;
import net.mcsweatshop.hexcastingadditions.common.hex.patterns.Chat;
import net.mcsweatshop.hexcastingadditions.common.hex.patterns.OpEvalAsynch;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.LinkedHashMap;
import java.util.Map;

import static at.petrak.hexcasting.common.lib.hex.HexActions.make;

@Mixin(HexActions.class)
public abstract class Whatever {


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
    private static final ActionRegistryEntry EvalAsynch = makes("asynch", new ActionRegistryEntry(HexPattern.fromAngles("wdwwd", HexDir.EAST), OpEvalAsynch.INSTANCE));
}
