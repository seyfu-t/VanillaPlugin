package Commands;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Point_cmd implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player && cmd.getName().equalsIgnoreCase("point")) {
			Player p = (Player)sender;
			File file = new File("plugins//Fix//points_"+p.getName()+".yml");
			Location loc = p.getLocation();
			
			if(!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			int len = args.length;
			String action="";
			String name="";
			if(len >= 1) action = args[0];
			if(len >= 2) name = args[1];
			
			String cmdMsg = "/point <save/delete/list> <Punktname>";
			
			if(len == 0 || len > 2 || (len == 2 && !args[0].equalsIgnoreCase("save") && !args[0].equalsIgnoreCase("delete"))) {
				p.sendMessage(cmdMsg);
				return false;
			}else if(len == 1) {
				if(action.equalsIgnoreCase("list")) {
					HashMap<String, Location> map =  read(file);
					if(map != null) {
						p.sendMessage("§b§lAlle deine Punkte: ");
						for(String locName : map.keySet()) {
							Location lo = map.get(locName);
							p.sendMessage(" ");
							p.sendMessage("§e§l"+locName+": ");
							p.sendMessage("§a--Welt: " + lo.getWorld().getName());
							p.sendMessage("§a--X: " + lo.getX());
							p.sendMessage("§a--Y: " + lo.getY());
							p.sendMessage("§a--Z: " + lo.getZ());
							p.sendMessage("§a--Yaw: " + lo.getYaw());
							p.sendMessage("§a--Pitch: " + lo.getPitch());
						}
					}else {
						p.sendMessage("§cFehler beim Lesen, gibt es überhaupt Einträge");
					}
				}else if(action.equalsIgnoreCase("save") || action.equalsIgnoreCase("delete")) {
					p.sendMessage("§cPunktname fehlt!");
					p.sendMessage(cmdMsg);
				}else {
					p.sendMessage("§cUnbekannter Befehl: §4$l"+args[0]);
					p.sendMessage(cmdMsg);
				}
			}else if(len == 2) {
				if(action.equalsIgnoreCase("save")) {
					if(write(file,name,loc)) {
						Location lo = p.getLocation();
						p.sendMessage("§e§l"+name+"§a gespeichert mit Location: ");
						p.sendMessage("§a--Welt: "+lo.getWorld().getName());
						p.sendMessage("§a--X: "+lo.getX());
						p.sendMessage("§a--Y: "+lo.getY());
						p.sendMessage("§a--Z: "+lo.getZ());
						p.sendMessage("§a--Yaw: "+lo.getYaw());
						p.sendMessage("§a--Pitch: "+lo.getPitch());
					}else {
						p.sendMessage("§cFehler beim Beschreiben, existiert der Name, §4§l"+args[1]+"§c, bereits?");
					}
				}else if(action.equalsIgnoreCase("delete")) {
					if(delete(file, args[1])) p.sendMessage("§a"+name+" wurde Erfolgreich gelöscht");
					else p.sendMessage("§cFehler beim Löschen, existiert der Name, §4§l"+args[1]+"§c, überhaupt?");
				}
			}
		}
		return false;
	}
	
	boolean write(File file, String name, Location loc) {
		new YamlConfiguration();
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		if(cfg.get(name) != null) {
			return false;
		}
		cfg.set(name, loc);
		try {
			cfg.save(file);
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	boolean delete(File file, String locName) {
		new YamlConfiguration();
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		if(cfg.getLocation(locName) == null) {
			return false;
		}
		cfg.set(locName, null);
		try {
			cfg.save(file);
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	HashMap<String, Location> read(File file){
		new YamlConfiguration();
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		HashMap<String, Location> map = new HashMap<String, Location>();
		for(String name : cfg.getKeys(false)) {
			map.put(name, cfg.getLocation(name));
		}
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
			map = null;
		}
		return map;
	}

}
