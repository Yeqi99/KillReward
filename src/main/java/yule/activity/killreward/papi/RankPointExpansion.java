package yule.activity.killreward.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yule.activity.killreward.ScoreRank;

public class RankPointExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "killReward";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Yeqi";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if(params.equalsIgnoreCase("points")){
            return player==null?null:ScoreRank.getScore(player.getPlayer())+"";
        }
        return null;
    }
}
