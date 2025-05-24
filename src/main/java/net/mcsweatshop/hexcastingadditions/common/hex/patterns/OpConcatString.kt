package net.mcsweatshop.hexcastingadditions.common.hex.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import net.mcsweatshop.hexcastingadditions.common.hex.utils.getString

object OpConcatString  : ConstMediaAction {
    override val argc: Int
        get() = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val string = args.getString(0)
        val stringEnd = args.getString(1)

        return ToString.stringVal(string+stringEnd);
    }
}