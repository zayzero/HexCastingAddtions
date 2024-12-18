package net.mcsweatshop.hexcastingadditions.common.net;

import java.io.Serializable;

public class Packet implements Serializable{
    public Serializable Object;
    public final PacketTypes TYPE;

    public Packet(Serializable object, PacketTypes type) {
        Object = object;
        TYPE = type;
    }


}
