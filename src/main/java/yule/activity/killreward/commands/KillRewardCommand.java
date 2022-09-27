package yule.activity.killreward.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import yule.activity.killreward.KillReward;

public class KillRewardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length==0){
            return true;
        }
        if(args[0].equalsIgnoreCase("reload")){
            KillReward.plugin.reloadConfig();
        }
        return true;
    }
}
