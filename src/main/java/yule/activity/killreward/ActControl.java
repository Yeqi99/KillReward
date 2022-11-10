package yule.activity.killreward;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import yeqi.tools.yeqilib.YeqiLib;
import yeqi.tools.yeqilib.message.Sender;
import yeqi.tools.yeqilib.time.TimeControl;
import yule.activity.killreward.listeners.DamageMob;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActControl {
    private static JavaPlugin plugin;
    public static boolean flag;
    public static void start(){
        plugin=KillReward.plugin;

        new BukkitRunnable(){
            @Override
            public void run(){
                String startTime=plugin.getConfig().getString("time.start");
                String endTime=plugin.getConfig().getString("time.end");
                String now= TimeControl.getStringTimeNow("HH:mm");
                //如果到达指定时间
                if(TimeControl.atNow(now,startTime)){
                    if(!flag){
                        String title= plugin.getConfig().getString("title.start.title");
                        String subTitle= plugin.getConfig().getString("title.start.subTitle");
                        int fadeIn= plugin.getConfig().getInt("title.start.fadeIn");
                        int fadeOut=  plugin.getConfig().getInt("title.start.fadeOut");
                        int stay= plugin.getConfig().getInt("title.start.stay");
                        new Sender(plugin).sendToAllTitle(title,subTitle,fadeIn,stay,fadeOut);
                        ScoreRank.isAct=true;
                        flag=true;
                    }
                }else if(TimeControl.atNow(now,endTime)){
                    if(flag){
                        ScoreRank.isAct=false;
                        String title= plugin.getConfig().getString("title.end.title");
                        String subTitle= plugin.getConfig().getString("title.end.subTitle");
                        int fadeIn= plugin.getConfig().getInt("title.end.fadeIn");
                        int fadeOut=  plugin.getConfig().getInt("title.end.fadeOut");
                        int stay= plugin.getConfig().getInt("title.end.stay");
                        new Sender(plugin).sendToAllTitle(title,subTitle,fadeIn,stay,fadeOut);
                        ScoreRank.look();
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                Bukkit.getScheduler().runTask(KillReward.plugin, ScoreRank::getRankReward);
                            }
                        }.runTaskAsynchronously(KillReward.plugin);
                        DamageMob.monopolies.clear();
                        flag=false;
                    }
                }
            }
        }.runTaskTimerAsynchronously(plugin,0,20);
    }
}
