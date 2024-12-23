package net.mcsweatshop.hexcastingadditions.mixin;

import at.petrak.hexcasting.api.HexAPI;
import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.PatternShapeMatch;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.eval.CastResult;
import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType;
import at.petrak.hexcasting.api.casting.eval.sideeffects.OperatorSideEffect;
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.casting.mishaps.MishapEvalTooMuch;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidPattern;
import at.petrak.hexcasting.api.casting.mishaps.MishapUnenlightened;
import at.petrak.hexcasting.api.mod.HexTags;
import at.petrak.hexcasting.common.casting.PatternRegistryManifest;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.mcsweatshop.hexcastingadditions.mixininterfaces.ActionMethods;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static at.petrak.hexcasting.api.utils.HexUtils.isOfTag;

@Mixin(PatternIota.class)
public abstract class VMOverrides {
    @Shadow public abstract HexPattern getPattern();

    @Inject(at=@At(value ="HEAD"),method = "execute", remap = false,cancellable = true)
    public void execute(CastingVM vm, ServerLevel world, SpellContinuation continuation, CallbackInfoReturnable<CastResult> cir) {
        cir.cancel();
        Supplier<Component> castedName = () -> {
            return null;
        };
        try {
            var lookup = PatternRegistryManifest.matchPattern(this.getPattern(), vm.getEnv(), false);
            vm.getEnv().precheckAction(lookup);

            Action action;
            if (lookup instanceof PatternShapeMatch.Normal || lookup instanceof PatternShapeMatch.PerWorld) {
                ResourceKey<ActionRegistryEntry> key;
                if (lookup instanceof PatternShapeMatch.Normal normal) {
                    key = normal.key;
                } else {
                    PatternShapeMatch.PerWorld perWorld = (PatternShapeMatch.PerWorld) lookup;
                    key = perWorld.key;
                }

                var reqsEnlightenment = isOfTag(IXplatAbstractions.INSTANCE.getActionRegistry(), key,
                        HexTags.Actions.REQUIRES_ENLIGHTENMENT);

                castedName = () -> HexAPI.instance().getActionI18n(key, reqsEnlightenment);
                action = Objects.requireNonNull(IXplatAbstractions.INSTANCE.getActionRegistry().get(key)).action();

                if (reqsEnlightenment && !vm.getEnv().isEnlightened()) {
                    // this gets caught down below
                    throw new MishapUnenlightened();
                }
            } else if (lookup instanceof PatternShapeMatch.Special special) {
                castedName = special.handler::getName;
                action = special.handler.act();
            } else if (lookup instanceof PatternShapeMatch.Nothing) {
                throw new MishapInvalidPattern();
            } else throw new IllegalStateException();

            // do the actual calculation!!
            var result = ((ActionMethods)action).operate(
                    vm.getEnv(),
                    vm.getImage(),
                    continuation,
                    vm
            );

            if (result.getNewImage().getOpsConsumed() > vm.getEnv().maxOpCount()) {
                throw new MishapEvalTooMuch();
            }

            var cont2 = result.getNewContinuation();
            // TODO parens also break prescience
            var sideEffects = result.getSideEffects();
            cir.setReturnValue(
            new CastResult(
                    (PatternIota)((Object)this),
                    cont2,
                    result.getNewImage(),
                    sideEffects,
                    ResolvedPatternType.EVALUATED,
                    result.getSound()));

        } catch (Mishap mishap) {
            cir.setReturnValue(new CastResult(
                    (PatternIota)((Object)this),
                    continuation,
                    null,
                    List.of(new OperatorSideEffect.DoMishap(mishap, new Mishap.Context(this.getPattern(), castedName.get()))),
                    mishap.resolutionType(vm.getEnv()),
                    HexEvalSounds.MISHAP));
        }
    }
}
