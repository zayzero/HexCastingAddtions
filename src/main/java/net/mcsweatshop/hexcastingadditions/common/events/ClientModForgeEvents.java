package net.mcsweatshop.hexcastingadditions.common.events;

import at.petrak.hexcasting.api.item.IotaHolderItem;
import net.mcsweatshop.hexcastingadditions.HexCastingAdditions;
import net.mcsweatshop.hexcastingadditions.common.hex.itemiota.Location;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid= HexCastingAdditions.MODID, bus=Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class ClientModForgeEvents {
    @SubscribeEvent
    public static void InventoryOpen(ContainerScreenEvent event){
        AbstractContainerScreen<?> containerScreen = event.getContainerScreen();
        CompoundTag toSend                                   = new CompoundTag();
        for (ItemStack itemStack : containerScreen.getMenu().getItems()) {
            if (!(itemStack.getItem() instanceof IotaHolderItem holderItem)) continue;
            CompoundTag hexcastingTag = holderItem.readIotaTag(itemStack);
            if (hexcastingTag==null||hexcastingTag.isEmpty()||!hexcastingTag.contains("hexcasting:data")) continue;
            CompoundTag hexcastingData = hexcastingTag.getCompound("hexcasting:data");
            if (!hexcastingData.contains("location")||!hexcastingData.getString("location").equals(Location.Inventory.name())) continue;
            if (!hexcastingData.contains("key")||!hexcastingData.contains("player")) continue;
            String key=hexcastingData.getString("key");
            String player=hexcastingData.getString("player");

            if (!toSend.contains(player)) toSend.put(player, new ListTag());
            toSend.getList(player, ListTag.TAG_STRING).add(StringTag.valueOf(key));

//            System.out.println("tag in item: "+ hexcastingTag);
        }
    }

}
