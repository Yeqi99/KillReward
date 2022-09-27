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
                        new Sender(plugin).sendToAllTitle("击杀竞赛活动开始了","请开始你的表演",20,5,20);
                        ScoreRank.isAct=true;
                        flag=true;
                    }
                }else if(TimeControl.atNow(now,endTime)){
                    if(flag){
                        ScoreRank.isAct=false;
                        new Sender(plugin).sendToAllTitle("击杀竞赛活动结束了","已发放排名奖励",20,5,20);
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
