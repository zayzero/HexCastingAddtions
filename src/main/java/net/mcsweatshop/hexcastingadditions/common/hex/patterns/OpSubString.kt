package net.mcsweatshop.hexcastingadditions.common.hex.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getInt
import at.petrak.hexcasting.api.casting.iota.Iota
import net.mcsweatshop.hexcastingadditions.common.hex.utils.getString

object OpSubString  : ConstMediaAction {
    override val argc: Int
        get() = 3

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val string = args.getString(0)
        val start = args.getInt(1)
        val end = args.getInt(2)

        return ToString.stringVal(string.substring(start,end));
    }
}