package net.mcsweatshop.hexcastingadditions.mixininterfaces;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;

import javax.annotation.Nonnull;

public interface ActionMethods {
    public at.petrak.hexcasting.api.casting.eval.OperationResult operate(CastingEnvironment env, CastingImage img, SpellContinuation continuation, CastingVM vm);
}
