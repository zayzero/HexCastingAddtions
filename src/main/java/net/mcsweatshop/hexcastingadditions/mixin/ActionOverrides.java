package net.mcsweatshop.hexcastingadditions.mixin;

import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import net.mcsweatshop.hexcastingadditions.mixininterfaces.ActionMethods;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nonnull;

@Mixin(Action.class)
public interface ActionOverrides extends ActionMethods{
    @Shadow public at.petrak.hexcasting.api.casting.eval.OperationResult operate(CastingEnvironment env, CastingImage img, SpellContinuation continuation);
    @Nonnull
    public default at.petrak.hexcasting.api.casting.eval.OperationResult operate(CastingEnvironment env, CastingImage img, SpellContinuation continuation, CastingVM vm){
        return this.operate(env,img,continuation);
    };
}
