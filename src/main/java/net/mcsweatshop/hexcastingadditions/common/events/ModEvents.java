package net.mcsweatshop.hexcastingadditions.common.events;

import net.mcsweatshop.hexcastingadditions.HexCastingAdditions;
import net.mcsweatshop.hexcastingadditions.common.net.PacketHandler;
import net.mcsweatshop.hexcastingadditions.common.util.ItemAdditions;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid= HexCastingAdditions.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(PacketHandler::init);
    }
}
