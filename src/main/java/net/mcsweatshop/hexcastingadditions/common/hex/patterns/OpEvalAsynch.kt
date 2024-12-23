package net.mcsweatshop.hexcastingadditions.common.hex.patterns

import at.petrak.hexcasting.api.casting.SpellList
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.circles.BlockEntityAbstractImpetus
import at.petrak.hexcasting.api.casting.circles.CircleExecutionState
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.env.CircleCastEnv
import at.petrak.hexcasting.api.casting.eval.vm.*
import at.petrak.hexcasting.api.casting.evaluatable
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.casting.iota.PatternIota
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidOperatorArgs
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import net.mcsweatshop.hexcastingadditions.common.events.ExecuteIotasEvent
import net.mcsweatshop.hexcastingadditions.common.events.ModForgeEvents
import net.mcsweatshop.hexcastingadditions.common.hex.utils.Utils
import net.mcsweatshop.hexcastingadditions.mixininterfaces.ActionMethods
import net.mcsweatshop.hexcastingadditions.mixininterfaces.AsyncRuns
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.server.ServerLifecycleHooks
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.*;


object OpEvalAsynch : Action {

    override fun operate(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation): OperationResult {
        val stack = image.stack.toMutableList()
        val iota = stack.removeLastOrNull() ?: throw MishapNotEnoughArgs(2, 0)
        if (!iota.executable()&&!Utils.isList(iota)) throw MishapInvalidOperatorArgs(listOf(iota))
        val ioa = stack.getDouble(stack.lastIndex)
        stack.removeLastOrNull() ?: throw MishapNotEnoughArgs(2, 1)
//        if (!Utils.isDouble(ioa))
        return exec(env, image, continuation, stack, iota, ioa)
    }

    fun exec(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation, newStack: MutableList<Iota>, iota: Iota, ioa: Double): OperationResult {
        // also, never make a break boundary when evaluating just one pattern
        val nullStack= NullIota()
        val instrs =evaluatable(iota, 0)
        val time=AtomicInteger(ioa.toInt())

        val newCont =
                if (instrs.left().isPresent || (continuation is SpellContinuation.NotDone && continuation.frame is FrameFinishEval)) {
                    continuation
                } else {
                    continuation.pushFrame(FrameFinishEval) // install a break-boundary after eval
                }

        val instrsList = instrs.map({ SpellList.LList(0, listOf(it)) }, { it })
        val frame = FrameEvaluate(instrsList, true)

        val holder=at.petrak.hexcasting.api.casting.SpellList.LList(0, listOf());
        val fakeFrame=at.petrak.hexcasting.api.casting.eval.vm.FrameEvaluate(holder, false)
        val image2 = image.withUsedOp().copy(stack = newStack)
        var enviorment=env
        if (env is CircleCastEnv) enviorment=env
        if (enviorment is CircleCastEnv) {
            val idk=enviorment.circleState().impetusPos;
            val impetus =enviorment.impetus
            if(impetus is AsyncRuns) {
                impetus.incrementAsync()
            }
            castCircle(time, iota, image2, enviorment, continuation, idk,impetus)
        }
        else cast(time,iota,image2,enviorment,continuation)
        continuation.pushFrame(frame)


        return OperationResult(image2, listOf(), newCont.pushFrame(fakeFrame), HexEvalSounds.HERMES)
    }

    private fun cast(time: AtomicInteger, iota: Iota, image: CastingImage, env: CastingEnvironment, cont: SpellContinuation): CompletableFuture<Void>? =
            CompletableFuture.runAsync {
                TimeUnit.MILLISECONDS.sleep(time.get().toLong())
                ModForgeEvents.toExecute.add(ExecuteIotasEvent(time,iota,image,env,cont,null,null))
            }
    private fun castCircle(time: AtomicInteger, iota: Iota, image: CastingImage, env: CircleCastEnv, cont: SpellContinuation, pos: BlockPos, impetus: BlockEntityAbstractImpetus?) {
        CompletableFuture.delayedExecutor(time.get().toLong(),TimeUnit.MILLISECONDS).execute { castCircleWrap(time,iota,image,env,cont,pos,impetus) }
    }
    private fun castCircleWrap(time: AtomicInteger, iota: Iota, image: CastingImage, env: CircleCastEnv, cont: SpellContinuation, pos: BlockPos, impetus: BlockEntityAbstractImpetus?) {
        TimeUnit.MILLISECONDS.sleep(time.get().toLong())
        ModForgeEvents.toExecute.add(ExecuteIotasEvent(time,iota,image,env,cont,pos,impetus))

    }
    private fun tellPlayers(msg: String){
        ServerLifecycleHooks.getCurrentServer().playerList.players.forEach {
            it.sendSystemMessage(Component.literal(msg))
        }
    }
    private fun blockEntityExists(pos: BlockPos,world: ServerLevel){
        tellPlayers("doesnt exists as entity: "+(world.getBlockEntity(pos)==null))
    }

}