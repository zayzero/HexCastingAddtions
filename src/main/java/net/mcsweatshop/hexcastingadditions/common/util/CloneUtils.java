package net.mcsweatshop.hexcastingadditions.common.util;

import at.petrak.hexcasting.api.casting.circles.BlockEntityAbstractImpetus;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.common.blocks.circles.impetuses.BlockEntityRedstoneImpetus;
import com.rits.cloning.Cloner;

public class CloneUtils {

    public static CastingEnvironment cloneEnv(CastingEnvironment toClone){
        Cloner cloner=new Cloner();
        return cloner.deepClone(toClone);
    }

    public static BlockEntityAbstractImpetus newImpetus(BlockEntityAbstractImpetus impetus){
        BlockEntityAbstractImpetus BEAI;
        try {
            BEAI=impetus;
        } catch (Exception e) {
            System.out.println("failed copy");
            BEAI=impetus;
        }
        return BEAI;

    }
}
