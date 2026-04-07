package net.mcsweatshop.hexcastingadditions.common.hex.patterns

import at.petrak.hexcasting.api.HexAPI
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import net.mcsweatshop.hexcastingadditions.common.hex.stringiota.StringIota
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.StringTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.minecraft.world.item.WritableBookItem
import net.minecraft.world.item.WrittenBookItem
import net.minecraftforge.registries.ForgeRegistries

object OpReadItem : Action{
    override fun operate(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation): OperationResult {
        val stack = image.stack.toMutableList()

        val rm = if (env.castingEntity!=null && env.castingEntity is Player) {
            val entity=env.castingEntity;
            returnIota(entity)
        } else {
            ""
        }
        stack.add(StringIota(rm))

        // does not mutate userdata
        val image2 = image.withUsedOp().copy(stack = stack)
        return OperationResult(image2, listOf(), continuation, HexEvalSounds.NORMAL_EXECUTE)
    }

    private fun returnIota(entity: LivingEntity?) : String{
        if (entity is Player && !entity.offhandItem.isEmpty) {
            val item = entity.offhandItem.item
            if (item is WritableBookItem) {
                var tag: CompoundTag? = entity.offhandItem.tag ?: return ""
                val array = tag?.get("pages")
                if (array !is ListTag) return ""
                var str = ""
                array.forEach {
                    if (it is StringTag) str += it.asString;
                }
                return str.replace("\\\\n", "\n")
            } else if(item is WrittenBookItem){
                var tag: CompoundTag? = entity.offhandItem.tag ?: return ""
                val array = tag?.get("pages")
                if (array !is ListTag) return ""
                var str = ""
                array.forEach {
                    if (it is StringTag){
                        val str2=Component.Serializer.fromJson(it.asString)
                        if (str2!=null) str += str2.string;
                    }
                }
                return str.replace("\\\\n", "\n")
            } else{
                return ForgeRegistries.ITEMS.getKey(item).toString()
            }
        }
        return "";
    }
}