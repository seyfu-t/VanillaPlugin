package Listener;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.kyori.adventure.text.Component;

public class JoinQuit_listener implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		int playtimeTicks = p.getStatistic(Statistic.PLAY_ONE_MINUTE);
	    int playtimeMinutes = playtimeTicks/(20*60);
	    int playtimeHours = playtimeMinutes / 60;
	    int playtimeMinutesRemainder = playtimeMinutes % 60;
	    String playtimeString = playtimeHours + "h" + playtimeMinutesRemainder + "min";
	    String displayName = "§7[§b§l" + playtimeString+"§7] §a" + p.getName();
	    
		e.joinMessage(Component.text(displayName+" §ajoined§7."));
		
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		int playtimeTicks = p.getStatistic(Statistic.PLAY_ONE_MINUTE);
	    int playtimeMinutes = playtimeTicks/(20*60);
	    int playtimeHours = playtimeMinutes / 60;
	    int playtimeMinutesRemainder = playtimeMinutes % 60;
	    String playtimeString = playtimeHours + "h" + playtimeMinutesRemainder + "min";
	    String displayName = "§7[§b§l" + playtimeString+"§7] §a" + p.getName();
		
		e.quitMessage(Component.text(displayName+" §cquit§7."));
	}

}
