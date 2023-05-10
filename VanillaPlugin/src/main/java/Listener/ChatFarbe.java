package Listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@SuppressWarnings("deprecation")
public class ChatFarbe implements Listener {
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		String msg = ChatColor.translateAlternateColorCodes('&', e.getMessage());
		e.setMessage(msg);
	}

}
