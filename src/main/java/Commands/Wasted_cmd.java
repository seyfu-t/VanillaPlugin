package Commands;

import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Wasted_cmd implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("wasted")) {
				int ticks = p.getStatistic(Statistic.PLAY_ONE_MINUTE);
				int h = (int) Math.floor(ticks/(20*60*60));
				int min = (int) (Math.floor(ticks/(20*60))-h*60);
				p.sendRichMessage("Time: "+h+"h "+min+"min");
			}
		}
		return true;
	}
	

}
