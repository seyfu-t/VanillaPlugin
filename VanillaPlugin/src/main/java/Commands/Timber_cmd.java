package Commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import Main.Start;

public class Timber_cmd implements CommandExecutor{
	File file = Start.timberFile;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("timber") && sender instanceof Player) {
				Player p = (Player)sender;
				boolean state = read(p);
				if(!write(p,!state)) {
					p.sendMessage("&cFehler");
				}else {
					p.sendMessage("Timber "+(!state ? "an":"aus"));
				}
		}
		return false;
	}
	public boolean write(Player p, boolean state) {
		new YamlConfiguration();
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set(p.getName(), state);
		try {
			cfg.save(file);
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	public boolean read(Player p) {
		new YamlConfiguration();
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(cfg.get(p.getName()) == null) {
			return false;
		}else {
			return cfg.getBoolean(p.getName());
		}
	}
}
