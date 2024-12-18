package net.mcsweatshop.hexcastingadditions.common.hex.patterns

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate.Either
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM
import at.petrak.hexcasting.api.casting.eval.vm.FrameFinishEval
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.evaluatable
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.casting.iota.PatternIota
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidOperatorArgs
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidSpellDatumType
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import net.mcsweatshop.hexcastingadditions.common.hex.utils.Utils
import java.util.ArrayList
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

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

        val holder=at.petrak.hexcasting.api.casting.SpellList.LList(0, listOf());
        val fakeFrame=at.petrak.hexcasting.api.casting.eval.vm.FrameEvaluate(holder, false)
        val image2 = image.withUsedOp().copy(stack = newStack)

        val vm=CastingVM(image, env)
        val what=CompletableFuture.runAsync {
            TimeUnit.MILLISECONDS.sleep(time.get().toLong())
            if (Utils.isList(iota)) {
                val ssss = Utils.IotaCastList(iota)
                val asList = ArrayList<Iota>()
                ssss?.list?.forEach {
                    asList.add(it)
                }
                vm.queueExecuteAndWrapIotas(asList, env.world)
            } else if (iota.executable()) {
                iota.execute(vm, env.world, continuation)
            }
        }
//        val listIotas= listOf()
//        CastingVM.empty(env).queueExecuteAndWrapIotas(listIotas, env.world)
//        iota.e
        return OperationResult(image2, listOf(), continuation.pushFrame(fakeFrame), HexEvalSounds.HERMES)
    }
}