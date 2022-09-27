package yule.activity.killreward;

import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import yeqi.tools.yeqilib.command.CommandRegister;
import yeqi.tools.yeqilib.listener.ListenerRegister;
import yule.activity.killreward.commands.KillRewardCommand;
import yule.activity.killreward.listeners.DamageMob;

public final class KillReward extends JavaPlugin {
    public static JavaPlugin plugin;
    @Override
    public void onEnable() {
        plugin=this;
        this.saveDefaultConfig();
        ListenerRegister.register(this,new DamageMob());
        CommandRegister.register(this,new KillRewardCommand(),"killreward");
    }

    @Override
    public void onDisable() {

    }
    public static Entity getEntity(ActiveMob mob){
        return mob.getEntity().getBukkitEntity();
    }
}
