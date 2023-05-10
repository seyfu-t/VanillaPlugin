
package Listener;

import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;

import Main.Start;

public class SaveAnnounce implements Listener{
	Start pl;
	public SaveAnnounce(Start s) {
		pl = s;
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSave(WorldSaveEvent e) {
		if(e.getWorld().getEnvironment() == Environment.NORMAL) {
			pl.getServer().broadcastMessage("§aWelt speichert.");
		}
	}

}
