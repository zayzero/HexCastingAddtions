package net.mcsweatshop.hexcastingadditions.common.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.function.Supplier;

public class ToServer {
    public final Packet packet;
    public ToServer(Packet packet){
        this.packet=packet;
    }

    public ToServer(FriendlyByteBuf friendlyByteBuf) {
        Packet mediator;
        try {
            mediator=SerializationUtils.deserialize(friendlyByteBuf.readByteArray());
        } catch (IOException | ClassNotFoundException e) {
            mediator=new Packet(null, PacketTypes.Null);
        }
        packet=mediator;
    }

    public void encode(FriendlyByteBuf friendlyByteBuf) {
        try {
            friendlyByteBuf.writeByteArray(SerializationUtils.serialize(packet));
        } catch (IOException e) {
            friendlyByteBuf.clear();
            friendlyByteBuf.writeByteArray(new byte[]{0});
        }
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
    }
}
