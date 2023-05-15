package Scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import net.kyori.adventure.text.Component;

public class TimePlayed {
	public void schedule(Plugin pl) {
	    Scoreboard sc = pl.getServer().getScoreboardManager().getMainScoreboard();
	    Objective obj;
	    if(sc.getObjective("playtime") == null) {//first time init Scoreboard
	        obj = sc.registerNewObjective("playtime", Criteria.DUMMY, Component.text("Play Time"));
	    } else {//init if already exists
	        obj = sc.getObjective("playtime");
	    }
	    
	    for (Player player : Bukkit.getOnlinePlayers()) {
	        formatScore(sc, obj, player);
	    }

	    new BukkitRunnable() {
	        @Override
	        public void run() {
	            for (Player player : Bukkit.getOnlinePlayers()) {
	                formatScore(sc, obj, player);
	            }
	        }
	    }.runTaskTimerAsynchronously(pl, 0L, 1200L);
	}

	public void formatScore(Scoreboard sc, Objective obj, Player p) {
	    Score score = obj.getScore(p.getName());
	    int playtimeTicks = p.getStatistic(Statistic.PLAY_ONE_MINUTE);
	    int playtimeMinutes = playtimeTicks/(20*60);
	    int playtimeHours = playtimeMinutes / 60;
	    int playtimeMinutesRemainder = playtimeMinutes % 60;
	    String playtimeString = playtimeHours + "h" + playtimeMinutesRemainder + "min";
	    score.setScore(playtimeMinutes);

	    String displayName = "§7[§b§l" + playtimeString+"§7] §a" + p.getName();
	    p.playerListName(Component.text(displayName));
	    p.setScoreboard(sc);
	}
	public void initPlayer(Player p, Plugin pl) {
		Scoreboard sc = pl.getServer().getScoreboardManager().getMainScoreboard();
	    Objective obj;
	    if(sc.getObjective("playtime") == null) {//first time init Scoreboard
	        obj = sc.registerNewObjective("playtime", Criteria.DUMMY, Component.text("Play Time"));
	    } else {//init if already exists
	        obj = sc.getObjective("playtime");
	    }
	    formatScore(sc, obj, p);
	}
}
