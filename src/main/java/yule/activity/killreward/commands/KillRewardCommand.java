package yule.activity.killreward.commands;

import hook.PlaceholderAPIHook;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import yule.activity.killreward.KillReward;
import yule.activity.killreward.papi.RankPointExpansion;

public class KillRewardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.isOp()){
            return true;
        }
        if(args.length==0){
            return true;
        }
        if(args[0].equalsIgnoreCase("reload")){
            KillReward.plugin.reloadConfig();
            if(PlaceholderAPIHook.isLoad){
                new RankPointExpansion().register();
            }
        }
        return true;
    }
}
