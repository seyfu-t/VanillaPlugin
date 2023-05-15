package Timber;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class utils {
  public static int getRandomInt(Integer max) {
    Random ran = new Random();
    return ran.nextInt(max.intValue());
  }
  
  public static boolean canBreakAll(ItemStack item, int i) {
    ItemMeta im = item.getItemMeta();
    Damageable dam = (Damageable)im;
    if (dam.hasDamage()) {
      int maxDur = item.getType().getMaxDurability();
      int currentDur = maxDur - dam.getDamage();
      if (currentDur > i)
        return true;
      return false;
    } 
    return true;
  }
  
  public static ItemMeta durabilityDmg(ItemStack item) {
    ItemMeta im = item.getItemMeta();
    Damageable dam = (Damageable)im;
    int ranI = getRandomInt(Integer.valueOf(100));
    boolean doDmg = true;
    if (im.hasEnchants() && 
      im.hasEnchant(Enchantment.DURABILITY)) {
      int unbreaking = im.getEnchantLevel(Enchantment.DURABILITY);
      if (unbreaking == 1) {
        if (ranI <= 20)
          doDmg = false; 
      } else if (unbreaking == 2) {
        if (ranI <= 27)
          doDmg = false; 
      } else if (unbreaking == 3 && 
        ranI <= 30) {
        doDmg = false;
      } 
    } 
    if (doDmg)
      dam.setDamage(dam.getDamage() + 1); 
    return (ItemMeta)dam;
  }
  
  public static List<Block> listLogs(Block b) {
    List<Block> allLogs = new ArrayList<Block>();
    if (isLog(b))
      allLogs.add(b); 
    allLogs.addAll(checkLogs(b));
    return allLogs;
  }
  
  public static List<Block> checkLogs(Block b) {
    List<Block> blocks = new ArrayList<Block>();
    blocks.addAll(logArea(b));
    Boolean con = Boolean.valueOf(areLogs(blocks));
    while (con.booleanValue()) {
      if (areLogs(blocks)) {
        blocks.addAll(addLogs(blocks));
        continue;
      } 
      con = Boolean.valueOf(false);
    } 
    return blocks;
  }
  
  public static List<Block> logArea(Block b) {
    List<Block> blocks = new ArrayList<Block>();
    for (BlockFace dir : checkDir()) {
      if (isLog(b.getRelative(dir)) && 
        !blocks.contains(b.getRelative(dir)))
        blocks.add(b.getRelative(dir)); 
    } 
    return blocks;
  }
  
  public static List<Block> addLogs(List<Block> bloc) {
    List<Block> blocks = new ArrayList<Block>();
    for (Block b : bloc) {
      for (BlockFace dir : checkDir()) {
        if (isLog(b.getRelative(dir))) {
          if (!blocks.contains(b.getRelative(dir)))
            blocks.add(b.getRelative(dir)); 
          continue;
        } 
        if (b.getRelative(dir).getType().name().toLowerCase().contains("leaves"))
          blocks.addAll(logArea(b.getRelative(dir))); 
      } 
    } 
    blocks.removeAll(bloc);
    return blocks;
  }
  
  public static boolean areLogs(List<Block> bloc) {
    List<Block> blocks = new ArrayList<Block>();
    for (Block b : bloc) {
      for (BlockFace dir : checkDir()) {
        if (isLog(b.getRelative(dir)) && 
          !blocks.contains(b.getRelative(dir)))
          blocks.add(b.getRelative(dir)); 
      } 
    } 
    blocks.removeAll(bloc);
    if (blocks.isEmpty())
      return false; 
    return true;
  }
  
  public static List<BlockFace> checkDir() {
    List<BlockFace> dir = new ArrayList<BlockFace>();
    dir.add(BlockFace.NORTH);
    dir.add(BlockFace.NORTH_EAST);
    dir.add(BlockFace.NORTH_WEST);
    dir.add(BlockFace.SOUTH);
    dir.add(BlockFace.SOUTH_EAST);
    dir.add(BlockFace.SOUTH_WEST);
    dir.add(BlockFace.EAST);
    dir.add(BlockFace.WEST);
    return dir;
  }
  
  public static boolean isLog(Block b) {
    if (b.getType().name().toLowerCase().contains("log"))
      return true; 
    return false;
  }
}
