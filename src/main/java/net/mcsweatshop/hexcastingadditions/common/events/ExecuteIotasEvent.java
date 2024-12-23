package net.mcsweatshop.hexcastingadditions.common.events;

import at.petrak.hexcasting.api.casting.circles.BlockEntityAbstractImpetus;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.env.CircleCastEnv;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import net.minecraft.core.BlockPos;

import java.util.concurrent.atomic.AtomicInteger;

public class ExecuteIotasEvent extends net.minecraftforge.eventbus.api.Event{
    public AtomicInteger time;
    public Iota iota;
    public CastingImage image;
    public CastingEnvironment env;
    public SpellContinuation cont;
    public BlockPos pos;
    public BlockEntityAbstractImpetus impetus;
    public ExecuteIotasEvent(AtomicInteger time, Iota iota, CastingImage image, CastingEnvironment env, SpellContinuation cont, BlockPos pos , BlockEntityAbstractImpetus impetus){
        this.time = time;
        this.iota = iota;
        this.image = image;
        this.env = env;
        this.cont = cont;
        this.pos = pos;
        this.impetus = impetus;
    }
}
