package net.mcsweatshop.hexcastingadditions.mixininterfaces;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.PatternIota;

import java.util.Map;

public interface HexActionsPatternsMap {
    public Map<Iota, PatternIota> getMap();
    public default PatternIota get(Iota iota){
        return getMap().get(iota);
    };
}
