package yule.activity.killreward.listeners.abs;

import org.bukkit.entity.Player;
import yeqi.tools.yeqilib.command.FormatCommandGroup;
import yeqi.tools.yeqilib.command.Processing;
import yule.activity.killreward.KillReward;
import yule.activity.killreward.ScoreRank;

import java.util.UUID;

public class Monopoly {
    public Player player;
    public UUID mobUUID;
    public String mmName;
    public boolean isMonopoly(UUID UUID){
        if (mobUUID.equals(UUID)){
            return true;
        }else {
            return false;
        }
    }
    public void getReward(){
        FormatCommandGroup fcp=new FormatCommandGroup(mmName,
                KillReward.plugin.getConfig().getStringList("mob_reward."+mmName+".commandGroup"));
        Processing.runFormatCommandGroup(player,fcp);
        ScoreRank.addScore(player,KillReward.plugin.getConfig().getDouble("mob_reward."+mmName+".score"));
    }
}
