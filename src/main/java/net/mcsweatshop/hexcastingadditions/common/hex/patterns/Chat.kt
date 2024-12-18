package net.mcsweatshop.hexcastingadditions.common.hex.patterns

import at.petrak.hexcasting.api.casting.*
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import net.mcsweatshop.hexcastingadditions.common.net.*
import net.minecraft.network.chat.ChatType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.phys.Vec3
import net.minecraftforge.server.ServerLifecycleHooks

object Chat : at.petrak.hexcasting.api.casting.castables.SpellAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val lhs = args.get(0)
        return SpellAction.Result(
                Spell(lhs,env.castingEntity),
                0,
                listOf(ParticleSpray.cloud(Vec3(0.0, 0.0,0.0),1.0))
        )
    }

    private data class Spell(val value: Iota, val castingEntity: LivingEntity?) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            ServerLifecycleHooks.getCurrentServer().playerList.players.forEach{
                it.sendSystemMessage(value.display())
            }
            ServerLifecycleHooks.getCurrentServer().sendSystemMessage(value.display())
        }
    }

}