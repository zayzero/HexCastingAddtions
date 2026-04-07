package net.mcsweatshop.hexcastingadditions.mixin;

import at.petrak.hexcasting.api.block.HexBlockEntity;
import at.petrak.hexcasting.api.casting.circles.BlockEntityAbstractImpetus;
import at.petrak.hexcasting.api.casting.circles.CircleExecutionState;
import net.mcsweatshop.hexcastingadditions.mixininterfaces.AsyncRuns;
import net.minecraft.core.BlockPos;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Mixin(BlockEntityAbstractImpetus.class)
public abstract class PauseEndExecutionCastingCircle extends HexBlockEntity implements WorldlyContainer, AsyncRuns {
    protected int runningAsync=0;
    protected boolean shouldCancel=false;

    public PauseEndExecutionCastingCircle(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    @Override
    public void incrementAsync() {
        runningAsync=runningAsync+1;
    }
    @Override
    public void decrementAsync() {
        runningAsync=runningAsync-1;

    }
    @Override
    public int getAsyncCount(){
        return runningAsync;
    };

    @Override
    public boolean shouldCancel(){
        return shouldCancel;
    }


    @Shadow @Nullable protected CircleExecutionState executionState;

    @Shadow public abstract @Nullable CircleExecutionState getExecutionState();

    @Shadow public abstract void endExecution();

//    @Inject(at=@At(value="HEAD"),remap = false,method = "endExecution",cancellable = true)
//    public void endExcutionAsync(CallbackInfo ci){
//        ci.cancel();
//        BlockEntityAbstractImpetus THIS = (BlockEntityAbstractImpetus) ((Object) this);
//        CompletableFuture.runAsync(()->{
//            try {
//                while (runningAsync > 0) {
//                    TimeUnit.MILLISECONDS.sleep(500);
//                }
//            } catch (Exception e){};
//            if (this.executionState== null)
//                return;
//
//            this.executionState.endExecution(THIS);
//        });
//    }

    @Inject(at=@At(value = "HEAD"),remap = false,method = "tickExecution",cancellable = true)
    public void tickExecution(CallbackInfo ci) {
        ci.cancel();
        if (this.level == null)
            return;

        this.setChanged();

        var state = this.getExecutionState();
        if (state == null) {
            return;
        }

        var shouldContinue = state.tick((BlockEntityAbstractImpetus) ((Object) this));

        if (!shouldContinue) {
            shouldCancel=false;
            CompletableFuture.runAsync(()->{
                try {
                    while (runningAsync > 0&&!shouldCancel) {
                        TimeUnit.MILLISECONDS.sleep(200);
                        if (this.isRemoved())shouldCancel=true;
                    }
                } catch (Exception e){};
                if (this.executionState== null)
                    return;
                this.endExecution();
                this.executionState = null;
            });
        } else
            this.level.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), getTickSpeed(state));
    }
    protected int getTickSpeed(CircleExecutionState state) {
        return Math.max(2, 10 - (state.reachedPositions.size() - 1) / 3);
    }

}
