package yule.activity.killreward.listeners;

import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import yeqi.tools.yeqilib.message.Sender;
import yeqi.tools.yeqilib.mythicmobs.MMBasic;
import yule.activity.killreward.KillReward;
import yule.activity.killreward.ScoreRank;
import yule.activity.killreward.listeners.abs.Monopoly;
import yule.activity.killreward.listeners.abs.PlayerInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static yeqi.tools.yeqilib.message.Color.toColor;

public class DamageMob implements Listener {
    public static List<Monopoly> monopolies=new ArrayList<>();
    //第一个攻击MM怪物者获得独占权，将独占加入列表
    @EventHandler
    public static void playerDamageMMob(EntityDamageByEntityEvent event){
        if(!ScoreRank.isAct){
            return;
        }
        Entity entity=event.getEntity();
        Entity damager=event.getDamager();
        if(damager instanceof Player){
            ActiveMob activeMob= MMBasic.getActiveMob(entity);
            if(activeMob!=null){
                if(!hasMonopoly(entity.getUniqueId())){
                    Monopoly monopoly=new Monopoly();
                    monopoly.mobUUID=entity.getUniqueId();
                    monopoly.player= (Player) damager;
                    monopoly.mmName=activeMob.getMobType();
                    monopolies.add(monopoly);
                }
            }
        }
    }
    //死亡后检测是否为独占怪物，如果是则将独占奖励获取并销毁对应独占
    @EventHandler
    public static void mmDeath(EntityDeathEvent event){
        if(!ScoreRank.isAct){
            return;
        }
        Entity entity=event.getEntity();
        Monopoly monopoly= getMonopoly(entity.getUniqueId());
        if(monopoly!=null){
            monopoly.getReward();
            monopolies.remove(monopoly);
        }
    }
    @EventHandler
    public static void PlayerDeathByPlayer(PlayerDeathEvent event){
        if(!ScoreRank.isAct){
            return;
        }
        Player dead=event.getEntity();
        Player killer=event.getEntity().getKiller();
        transferMonopoly(dead,killer);
    }
    public static boolean hasMonopoly(UUID mobUUID){
        for(Monopoly monopoly:monopolies){
            if( monopoly.isMonopoly(mobUUID)) {
                return true;
            }
        }
        return false;
    }
    public static Monopoly getMonopoly(UUID mobUUID){
        for(Monopoly monopoly:monopolies){
            if( monopoly.isMonopoly(mobUUID)) {
                return monopoly;
            }
        }
        return null;
    }
    public static void transferMonopoly(Player dead,Player killer){
        for(Monopoly monopoly:monopolies){
            if(monopoly.player.equals(dead)){
                monopoly.player=killer;
            }
        }
    }
}
