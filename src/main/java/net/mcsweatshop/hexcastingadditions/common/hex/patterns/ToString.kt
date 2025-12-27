package net.mcsweatshop.hexcastingadditions.common.hex.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.*
import net.mcsweatshop.hexcastingadditions.common.hex.stringiota.StringIota
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.Vec3
import net.minecraftforge.registries.ForgeRegistries
import java.lang.Math.floor
import kotlin.String


object ToString : ConstMediaAction{
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val value = args.get(0)
        return stringVal(getString(value));
    }
     fun getString(value: Iota) : String {
         return if (value is EntityIota){
             var entity=value.entity;
             if (ForgeRegistries.ENTITY_TYPES.getKey(entity.type)==null) return "";
             if (entity is Player) return entity.displayName.string;
             if (entity is ItemEntity) return ForgeRegistries.ITEMS.getKey(entity.item.item).toString();
             ForgeRegistries.ENTITY_TYPES.getKey(entity.type).toString()
         } else if(value is DoubleIota){
            return (floor(value.double*100) /100).toString();
         } else if(value is BooleanIota){
            return value.bool.toString();
         } else if(value is Vec3Iota) {
             fun toString(vec: Vec3): String {
                 return "(" + (floor(vec.x*1000) /1000) + ", " + (floor(vec.y*1000) /1000) + ", " + (floor(vec.z*1000) /1000) + ")"
             }
             return toString(value.vec3);
         }else if(value is ListIota) {
             var str="[";
             value.list.forEach{
                 str += getString(it) + ", ";
             }

             str=str.substring(0,str.length-2)
             str += "]"
             return str;
         }
         else if (value is StringIota){
             return value.string
         }
         else{
             return ""
         }
     }

    fun stringVal(str: String): List<StringIota>{
        return listOf(StringIota(str))
    }
}