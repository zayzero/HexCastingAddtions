package net.mcsweatshop.hexcastingadditions.common.net;

import net.mcsweatshop.hexcastingadditions.HexCastingAdditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public final static SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
                    new ResourceLocation(HexCastingAdditions.MODID, "main"))
            .serverAcceptedVersions((status) -> true)
            .clientAcceptedVersions((status) -> true)
            .networkProtocolVersion(() -> PacketHandler.PROTOCOL_VERSION).simpleChannel();

    private PacketHandler(){}
    public static void init() {
        int index = 0;
        INSTANCE.messageBuilder(ToServer.class,index++, NetworkDirection.PLAY_TO_SERVER).encoder(ToServer::encode).decoder(ToServer::new).consumerMainThread(ToServer::handle).add();

        INSTANCE.messageBuilder(FromServer.class,index++, NetworkDirection.PLAY_TO_CLIENT).encoder(FromServer::encode).decoder(FromServer::new).consumerMainThread(FromServerHandler::handle).add();

    }

}
