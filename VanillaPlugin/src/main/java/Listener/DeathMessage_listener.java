package Listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathMessage_listener implements Listener {
	@SuppressWarnings({ "unchecked" })
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
		Player p = e.getPlayer();
		File file = new File("plugins//Fix//deaths.yml");
		Location loc = p.getLocation();
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		new YamlConfiguration();
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		ArrayList<Location> list;
		if (cfg.getList(p.getName()) != null) {
			list = (ArrayList<Location>) cfg.getList(p.getName());
		} else {
			list = new ArrayList<Location>();
		}
		list.add(loc);
		
		cfg.set(p.getName(), list);
		try {
			cfg.save(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		int x = (int) loc.getX();
		int y = (int) loc.getY();
		int z = (int) loc.getZ();
		p.sendMessage("§cDeine Todes-Koordinaten: " + x + ", " + y + ", " + z);

//		String cause = e.getEntity().getLastDamageCause().getCause().name();
//		switch (cause) {
//		case "FALL": {
//			e.deathMessage(Component.text("§4§l" + p.getName() + " §cist an zu viel Fallschaden gestorben"));
//		}
//		case "ENTITY_ATTACK": {
//
//		}
//		case "PROJECTILE": {
//
//		}
//		case "": {
//
//		}
//		case "": {
//
//		}
//		case "": {
//
//		}
//		case "": {
//
//		}
//		case "": {
//
//		}
//		case "": {
//
//		}
//		case "": {
//
//		}
//		case "": {
//
//		}
//		case "": {
//
//		}
//		default:
//		}
	}
}
