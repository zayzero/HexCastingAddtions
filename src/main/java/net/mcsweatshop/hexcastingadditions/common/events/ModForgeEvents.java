package net.mcsweatshop.hexcastingadditions.common.events;

import at.petrak.hexcasting.api.casting.circles.BlockEntityAbstractImpetus;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.env.CircleCastEnv;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import net.mcsweatshop.hexcastingadditions.HexCastingAdditions;
import net.mcsweatshop.hexcastingadditions.common.hex.utils.Utils;
import net.mcsweatshop.hexcastingadditions.mixininterfaces.AsyncRuns;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid= HexCastingAdditions.MODID, bus=Mod.EventBusSubscriber.Bus.FORGE)
public class ModForgeEvents {
    public static List<ExecuteIotasEvent> toExecute=new ArrayList<>();
//    @SubscribeEvent
    public static void execute(ExecuteIotasEvent event){
        if (event.env instanceof CircleCastEnv) executeCircle(event);
        else executeCast(event);
    }
    public static void executeCircle(ExecuteIotasEvent event){
        BlockEntityAbstractImpetus impetusi=event.impetus;
        CastingImage image = event.image;
        CastingEnvironment env=event.env;
        Iota iota=event.iota;
        if (!(impetusi instanceof AsyncRuns impetus)) return;
        if (impetus.shouldCancel()) {
            impetus.decrementAsync();
            return;
        }
//        val f=CircleCastEnv(ServerLifecycleHooks.getCurrentServer().overworld(),env.circleState())
        CastingVM vm = new CastingVM(image, env);
        if (Utils.isList(iota)) {
            ArrayList<Iota> ssss = new ArrayList<Iota>();
            Utils.IotaCastList(iota).getList().forEach(ssss::add);

            vm.queueExecuteAndWrapIotas(ssss, env.getWorld());
        } else {
            if (iota instanceof PatternIota patternIota)
                vm.queueExecuteAndWrapIota(patternIota, env.getWorld());
        }
        impetus.decrementAsync();
    }
    public static void executeCast(ExecuteIotasEvent event){
        CastingImage image = event.image;
        CastingEnvironment env=event.env;
        Iota iota=event.iota;

//        val f=CircleCastEnv(ServerLifecycleHooks.getCurrentServer().overworld(),env.circleState())
        CastingVM vm = new CastingVM(image, env);
        if (Utils.isList(iota)) {
            ArrayList<Iota> ssss = new ArrayList<Iota>();
            Utils.IotaCastList(iota).getList().forEach(ssss::add);

            vm.queueExecuteAndWrapIotas(ssss, env.getWorld());
        } else {
            if (iota instanceof PatternIota patternIota)
                vm.queueExecuteAndWrapIota(patternIota, env.getWorld());
        }
    }
    public static void println(String str){
        System.out.println(str);
    }

    @SubscribeEvent
    public static void tick(TickEvent.ServerTickEvent event){
        if (ServerLifecycleHooks.getCurrentServer()==null) return;
        if (!toExecute.isEmpty()) {
            toExecute.forEach(ModForgeEvents::execute);
            toExecute.clear();
        }
    }

}
