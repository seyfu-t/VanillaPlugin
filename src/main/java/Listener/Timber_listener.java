package Listener;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import Commands.Timber_cmd;
import Timber.utils;

public class Timber_listener implements Listener {
	private Plugin plugin;

	public Timber_listener(Plugin pl) {
		plugin = pl;
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Block b = e.getBlock();
		final Player p = e.getPlayer();
		Timber_cmd cc = new Timber_cmd();
		if (cc.read(p)) {
			PlayerInventory inv = p.getInventory();
			final ItemStack pickaxe = inv.getItemInMainHand();
			Material itemMat = pickaxe.getType();

			if ((b.getType().name().toLowerCase().contains("log") || b.getType().name().toLowerCase().contains("stem"))
					&& (itemMat == Material.WOODEN_AXE || itemMat == Material.STONE_AXE || itemMat == Material.IRON_AXE
							|| itemMat == Material.GOLDEN_AXE || itemMat == Material.DIAMOND_AXE
							|| itemMat == Material.NETHERITE_AXE)
					&& !p.isSneaking()) {
				Block seqB = b;
				final List<Block> blocks = new ArrayList<Block>();
				for (int i = 0; i < 30; i++) {
					if (!blocks.contains(seqB)) {
						blocks.addAll(utils.listLogs(seqB));
						if (utils.isLog(seqB.getRelative(BlockFace.UP))) {
							seqB = seqB.getRelative(BlockFace.UP);
						} else {
							List<Block> listB = new ArrayList<Block>();
							listB.add(seqB.getRelative(BlockFace.UP));
							if (utils.areLogs(listB)) {
								listB.addAll(utils.addLogs(listB));
								for (int g = 0; g < listB.size(); g++)
									seqB = listB.get(g);
							} else {
								i = 30;
							}
						}
					}
				}
				if (utils.canBreakAll(pickaxe, blocks.size())) {
					
					(new BukkitRunnable() {
						int count = 0;

						public void run() {
							if (this.count >= blocks.size()) {
								cancel();
								return;
							}
							((Block) blocks.get(this.count)).breakNaturally();
							if (p.getGameMode() != GameMode.CREATIVE)
								pickaxe.setItemMeta(utils.durabilityDmg(pickaxe));
							this.count++;
						}
					}).runTaskTimer(plugin, 3L, 2L);
				}
			}
		}
	}
}
