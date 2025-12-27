package net.mcsweatshop.hexcastingadditions.mixin;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.casting.actions.lists.OpIndex;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import at.petrak.hexcasting.interop.HexInterop;
import net.mcsweatshop.hexcastingadditions.HexCastingAdditions;
import net.mcsweatshop.hexcastingadditions.common.hex.patterns.*;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Supplier;

import static at.petrak.hexcasting.common.lib.hex.HexActions.make;
import static at.petrak.hexcasting.interop.HexInterop.PATCHOULI_ANY_INTEROP_FLAG;

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
    @Nullable
    private static ActionRegistryEntry makesIfPresent(String classString,String name, Supplier<ActionRegistryEntry> are) {

        try {
            Class<?> clazz = Class.forName(classString);
            HexCastingAdditions.CosmeticArmorExists.set(true);
            PatchouliAPI.get().setConfigFlag(PATCHOULI_ANY_INTEROP_FLAG, true);

        } catch (Exception e){
            return null;
        }

        ActionRegistryEntry old = (ActionRegistryEntry)ACTIONS.put(new ResourceLocation(HexCastingAdditions.MODID,name), are.get());
        if (old != null) {
            throw new IllegalArgumentException("Typo? Duplicate id " + name);
        } else {

            return are.get();
        }
    }

    @Unique
    private static final ActionRegistryEntry HANDIOTA = makes("handitemiota", new ActionRegistryEntry(HexPattern.fromAngles("qwewee", HexDir.WEST), OpHandAsItemIota.INSTANCE));
    private static final ActionRegistryEntry ARMORIOTA = makes("armoritemiota", new ActionRegistryEntry(HexPattern.fromAngles("qweweee", HexDir.WEST), OpGetArmorSlot.INSTANCE));
    private static final ActionRegistryEntry COSMETICARMORIOTA = makesIfPresent("lain.mods.cos.api.CosArmorAPI","cosmeticarmoritemiota",()-> new ActionRegistryEntry(HexPattern.fromAngles("qweweeedd", HexDir.WEST), OpGetCosmeticArmorSlot.INSTANCE));
    private static final ActionRegistryEntry INVENTORYIOTA = makes("inventoryitemiota",new ActionRegistryEntry(HexPattern.fromAngles("qweeede", HexDir.WEST), OpInventoryItemIota.INSTANCE));
    private static final ActionRegistryEntry INVENTORYIOTAII = makes("inventorytwoitemiota",new ActionRegistryEntry(HexPattern.fromAngles("qqqqqdaqaaweaweeede", HexDir.SOUTH_EAST), OpInventoryByPlayerItemIota.INSTANCE));
    private static final ActionRegistryEntry ENTITYIOTA = makes("entityitemiota",new ActionRegistryEntry(HexPattern.fromAngles("qwewewaqaweee", HexDir.WEST), OpEntityAsItem.INSTANCE));
    private static final ActionRegistryEntry BLOCKENTITYIOTA = makes("blockentityitem", new ActionRegistryEntry(HexPattern.fromAngles("qweweeeee", HexDir.WEST), OpGetTileEntityItem.INSTANCE));

    private static final ActionRegistryEntry INVENTORYENCRYPT = makes("encryptinventory",new ActionRegistryEntry(HexPattern.fromAngles("eedwqqqwqq", HexDir.NORTH_EAST), OpEncryptInventory.INSTANCE));
    private static final ActionRegistryEntry ITEMENCRYPT = makes("encryptitem",new ActionRegistryEntry(HexPattern.fromAngles("eedweeeweewee", HexDir.SOUTH_WEST), EncryptItem.INSTANCE));
    private static final ActionRegistryEntry ITEMDECRYPT = makes("decryptitem",new ActionRegistryEntry(HexPattern.fromAngles("qqawqqqwqqwqq", HexDir.SOUTH_EAST), DecryptItem.INSTANCE));

    private static final ActionRegistryEntry CHANGEDELAY = makes("changedelay", new ActionRegistryEntry(HexPattern.fromAngles("eaqaawawwawwqwwaww", HexDir.WEST), OpChangeDelay.INSTANCE));
    private static final ActionRegistryEntry GRABITEM = makes("grabitem", new ActionRegistryEntry(HexPattern.fromAngles("qweweeeeew", HexDir.SOUTH_EAST), GrabItemSpell.INSTANCE));
    private static final ActionRegistryEntry SWAPITEM = makes("swapitem", new ActionRegistryEntry(HexPattern.fromAngles("qweweeeeeww", HexDir.SOUTH_EAST),SwapItemSpell.INSTANCE));


    private static final ActionRegistryEntry CHAT = makes("chat", new ActionRegistryEntry(HexPattern.fromAngles("deawqqa", HexDir.NORTH_EAST), Chat.INSTANCE));

    private static final ActionRegistryEntry EMPTYSTRING = makes("emptystring", new ActionRegistryEntry(HexPattern.fromAngles("aqadaqa", HexDir.SOUTH_EAST), OpEmptyString.INSTANCE));
    private static final ActionRegistryEntry TOCODEPOINT = makes("tocodepoint", new ActionRegistryEntry(HexPattern.fromAngles("aedw", HexDir.WEST), OpGetCharNum.INSTANCE));
    private static final ActionRegistryEntry FROMCODEPOINT = makes("fromcodepoint", new ActionRegistryEntry(HexPattern.fromAngles("aedwd", HexDir.WEST), OpGetCharFromCodePoint.INSTANCE));
    private static final ActionRegistryEntry TOSTRING = makes("tostring", new ActionRegistryEntry(HexPattern.fromAngles("aed", HexDir.WEST), ToString.INSTANCE));
    private static final ActionRegistryEntry READSTRING = makes("readstring", new ActionRegistryEntry(HexPattern.fromAngles("aede", HexDir.WEST), OpReadItem.INSTANCE));
    private static final ActionRegistryEntry CLIPBOARD = makes("clipboard", new ActionRegistryEntry(HexPattern.fromAngles("aedeaa", HexDir.WEST), OpClipBoard.INSTANCE));
    private static final ActionRegistryEntry CHARAT = makes("charat", new ActionRegistryEntry(HexPattern.fromAngles("aq", HexDir.WEST), OpGetCharAtIndex.INSTANCE));
    private static final ActionRegistryEntry INDEXOF = makes("indexof", new ActionRegistryEntry(HexPattern.fromAngles("aqadaad", HexDir.SOUTH_EAST), OpGetIndexOf.INSTANCE));
    private static final ActionRegistryEntry SUBSTRING = makes("substring", new ActionRegistryEntry(HexPattern.fromAngles("aqd", HexDir.WEST), OpSubString.INSTANCE));
    private static final ActionRegistryEntry STRINGSEGMENT = makes("stringsegment", new ActionRegistryEntry(HexPattern.fromAngles("aqdqqqqq", HexDir.WEST), OpStringSegment.INSTANCE));
    private static final ActionRegistryEntry CONCATSTRING = makes("concatstring", new ActionRegistryEntry(HexPattern.fromAngles("waawe", HexDir.NORTH_EAST), OpConcatString.INSTANCE));
    private static final ActionRegistryEntry STRINGTODOUBLE = makes("stringtodouble", new ActionRegistryEntry(HexPattern.fromAngles("aedq", HexDir.NORTH_EAST), OpStringToFloat.INSTANCE));
    private static final ActionRegistryEntry STRINGISDOUBLE = makes("isstringdouble", new ActionRegistryEntry(HexPattern.fromAngles("aeda", HexDir.NORTH_EAST), OpIsValidNum.INSTANCE));
    private static final ActionRegistryEntry STRINGLEN = makes("stringlen", new ActionRegistryEntry(HexPattern.fromAngles("qqqaa", HexDir.NORTH_WEST), OpStringLen.INSTANCE));
    private static final ActionRegistryEntry PATTERNFROMSTRIN = makes("patternstring", new ActionRegistryEntry(HexPattern.fromAngles("qqqqqeawddw", HexDir.SOUTH_EAST), OpPatternFromString.INSTANCE));



    private static final ActionRegistryEntry EvalAsynch = makes("asynch", new ActionRegistryEntry(HexPattern.fromAngles("wdwwd", HexDir.EAST), OpEvalAsynch.INSTANCE));
}
