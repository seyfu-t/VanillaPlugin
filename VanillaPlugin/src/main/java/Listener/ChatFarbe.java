package Listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@SuppressWarnings("deprecation")
public class ChatFarbe implements Listener {
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
	   Player p = e.getPlayer();
	   String msg = e.getMessage();
	   String prefix = "§7[§a§l"+p.getName()+"§7] ";
	   e.setFormat(prefix + ChatColor.translateAlternateColorCodes('&', msg));
	}
//	public Component convertHexToColor(String message) {
//	    Component component = Component.empty();
//
//	    int i = 0;
//	    while (i < message.length()) {
//	        if (message.charAt(i) == '#') {
//	            String hexCode = message.substring(i + 1, i + 7);
//	            component = component.append(Component.text("", Style.style(TextColor.fromHexString(hexCode))));
//	            i += 7;
//	        } else {
//	            int nextHash = message.indexOf("#", i);
//	            if (nextHash == -1) {
//	                nextHash = message.length();
//	            }
//	            component = component.append(Component.text(message.substring(i, nextHash)));
//	            i = nextHash;
//	        }
//	    }
//
//	    return component;
//	}
}
