package net.mcsweatshop.hexcastingadditions.common.hex.actions

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.api.casting.iota.PatternIota
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.api.mod.HexTags
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.common.lib.hex.HexActions
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import at.petrak.hexcasting.datagen.tag.HexActionTagProvider
import at.petrak.hexcasting.forge.xplat.ForgeXplatImpl
import at.petrak.hexcasting.xplat.IXplatAbstractions
import com.samsthenerd.inline.xplat.IXPlatAbstractions
import net.mcsweatshop.hexcastingadditions.common.hex.patterns.Chat
import net.mcsweatshop.hexcastingadditions.common.hex.utils.Utils.CreateRegistryEntry
import net.mcsweatshop.hexcastingadditions.common.hex.utils.Utils.modLoc
import net.minecraft.resources.ResourceLocation

object Patterns{

    @JvmField
    var PATTERNS: MutableList<Pair<ActionRegistryEntry, String>> = ArrayList()
    @JvmField
    var PER_WORLD_PATTERNS: MutableList<Pair<ActionRegistryEntry, String>> = ArrayList()
    @JvmField
    val SPECIAL_HANDLERS: MutableList<Pair<ResourceLocation, SpecialHandler>> = ArrayList()
    @JvmStatic
    fun registerPatterns() {
        try {
            for ((entry, location) in PATTERNS) {
                println("adding: $location");
                HexActions.make(location, entry);
            }
            for ((location, handler) in SPECIAL_HANDLERS)
                SPECIAL_HANDLERS.add(Pair(location, handler))
        } catch (e: Exception){
            println(e.message)
        }
    }

    @JvmField
    val chat = make(HexPattern.fromAngles("wdwww", HexDir.EAST), "chat/great", Chat,true)


    fun make (pattern: HexPattern, location: String, operator: Action, isPerWorld: Boolean = false): PatternIota {
        val pair = Pair(CreateRegistryEntry(pattern,operator),location)
        if (isPerWorld)
            PATTERNS.add(pair)
        else
            PATTERNS.add(pair)
        return PatternIota(pattern)
    }

}