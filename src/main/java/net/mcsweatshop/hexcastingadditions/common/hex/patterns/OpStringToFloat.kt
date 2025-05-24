package net.mcsweatshop.hexcastingadditions.common.hex.patterns

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getInt
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import net.mcsweatshop.hexcastingadditions.common.hex.stringiota.StringIota
import net.mcsweatshop.hexcastingadditions.common.hex.utils.Utils
import net.mcsweatshop.hexcastingadditions.common.hex.utils.getString

object OpStringToFloat  : ConstMediaAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val string = args.getString(0)
        return listOf(StringIota.toDoubleIota(string));
    }
}