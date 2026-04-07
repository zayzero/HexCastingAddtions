package net.mcsweatshop.hexcastingadditions.mixin;

import at.petrak.hexcasting.api.casting.circles.BlockEntityAbstractImpetus;
import at.petrak.hexcasting.api.casting.circles.CircleExecutionState;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.env.CircleCastEnv;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CircleCastEnv.class)
public abstract class CircleEnvironment extends CastingEnvironment {
    @Shadow @Final protected CircleExecutionState execState;
    public BlockEntityAbstractImpetus impetus=null;

    protected CircleEnvironment(ServerLevel world) {
        super(world);
    }

    @Inject(at=@At("HEAD"),remap = false,method = "getImpetus",cancellable = true)
    public void overrideGetImpetus(CallbackInfoReturnable<BlockEntityAbstractImpetus> cir){
        cir.cancel();
        if (impetus==null){
            CircleCastEnv THIS = (CircleCastEnv) ((Object) this);
            BlockEntity entity = this.world.getBlockEntity(this.execState.impetusPos);
            impetus= entity instanceof BlockEntityAbstractImpetus ? (BlockEntityAbstractImpetus)entity : null;
        }
        cir.setReturnValue(impetus);
    }

}
