package yule.activity.killreward;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import yeqi.tools.yeqilib.command.FormatCommandGroup;
import yeqi.tools.yeqilib.command.Processing;
import yeqi.tools.yeqilib.message.Sender;
import yule.activity.killreward.listeners.abs.PlayerInfo;

import java.util.ArrayList;
import java.util.List;

import static yeqi.tools.yeqilib.message.Color.toColor;

public class ScoreRank {
    public static List<PlayerInfo> playerInfoList=new ArrayList<>();
    public static boolean isAct;
    public static void addScore(Player player,double score){
        if(hasScore(player)){
            setScore(player,score+getScore(player));
        }else {
            PlayerInfo playerInfo=new PlayerInfo();
            playerInfo.player=player;
            playerInfo.score=score;
            playerInfoList.add(playerInfo);
        }
    }
    public static boolean hasScore(Player player){
        for (PlayerInfo playerInfo:playerInfoList){
            if (playerInfo.player==player){
                return true;
            }
        }
        return false;
    }
    public static boolean setScore(Player player,double score){
        for (PlayerInfo playerInfo:playerInfoList){
            if (playerInfo.player==player){
                playerInfo.score=score;
                return true;
            }
        }
        return false;
    }
    public static double getScore(Player player){
        for (PlayerInfo playerInfo:playerInfoList){
            if (playerInfo.player==player){
                return playerInfo.score;
            }
        }
        return -1;
    }
    public static void getSort(){
        for(int i=0;i<playerInfoList.size()-1;i++){
            for(int j=i;j<playerInfoList.size();j++){
                PlayerInfo a=playerInfoList.get(i);
                PlayerInfo b=playerInfoList.get(j);
                if(a.score<b.score){
                    PlayerInfo temp=a;
                    a=b;
                    b=temp;
                    playerInfoList.set(i,a);
                    playerInfoList.set(j,b);
                }
            }
        }
    }

    public static void getRankReward(){
        getSort();
        for(int i=0;i<playerInfoList.size();i++){
            Player player=playerInfoList.get(i).player;
            List<String> cmdGroup=KillReward.plugin.getConfig().getStringList("rank_reward."+(i+1));
            if (cmdGroup.size()>0){
                FormatCommandGroup fcp=new FormatCommandGroup(player.getDisplayName(),cmdGroup);
                Processing.runFormatCommandGroup(player,fcp);
            }else {
                cmdGroup=KillReward.plugin.getConfig().getStringList("rank_reward.normal");
                FormatCommandGroup fcp=new FormatCommandGroup(player.getDisplayName(),cmdGroup);
                Processing.runFormatCommandGroup(player,fcp);
            }
        }
        playerInfoList.clear();
    }
    public static void look(){
        for(PlayerInfo playerInfo:playerInfoList){
            Bukkit.broadcastMessage(toColor( playerInfo.player.getDisplayName()+":"
                    +playerInfo.score));
        }
    }
}
