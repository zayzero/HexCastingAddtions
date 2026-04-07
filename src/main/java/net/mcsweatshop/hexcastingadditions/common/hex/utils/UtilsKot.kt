//@file:JvmName("OperatorUtils")
package net.mcsweatshop.hexcastingadditions.common.hex.utils

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import net.mcsweatshop.hexcastingadditions.common.hex.stringiota.StringIota


fun List<Iota>.getString(idx: Int, argc: Int = 0): String {
    val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    if (x is StringIota) {
        return x.string
    } else {
        // TODO: I'm not sure this calculation is correct
        throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "string")
    }
}

