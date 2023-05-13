package Main;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import Commands.Point_cmd;
import Commands.Timber_cmd;
import Commands.Wasted_cmd;
import Listener.ChatFarbe;
import Listener.DeathMessage_listener;
import Listener.JoinQuit_listener;
import Listener.SaveAnnounce;
import Listener.Timber_listener;
import net.kyori.adventure.text.Component;

public class Start extends JavaPlugin {

	public static File timberFile = new File("plugins//Fix//timber.yml");

	@Override
	public void onEnable() {
		if (!timberFile.exists()) {
			try {
				timberFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		schedule();
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new ChatFarbe(), this);
		pm.registerEvents(new DeathMessage_listener(), this);
		pm.registerEvents(new SaveAnnounce(this), this);
		pm.registerEvents(new Timber_listener(this), this);
		pm.registerEvents(new JoinQuit_listener(), this);

		this.getCommand("wasted").setExecutor(new Wasted_cmd());
		this.getCommand("point").setExecutor(new Point_cmd());
		this.getCommand("timber").setExecutor(new Timber_cmd());
	}

	@Override
	public void onDisable() {

	}
	
	void schedule() {
	    Scoreboard sc = this.getServer().getScoreboardManager().getMainScoreboard();
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
	    }.runTaskTimerAsynchronously(this, 0L, 1200L);
	}

	void formatScore(Scoreboard sc, Objective obj, Player p) {
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

}
