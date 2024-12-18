package net.mcsweatshop.hexcastingadditions.common.net;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FromServerHandler {
    public static void handle(FromServer fromServer, Supplier<NetworkEvent.Context> contextSupplier) {
        switch (fromServer.packet.TYPE){
            case Message -> Message((String) fromServer.packet.Object);
        }
    }

    private static void Message(String object) {
        Minecraft.getInstance().player.displayClientMessage(Component.literal(object),false);
//        CastingEnvironment.
    }
}
