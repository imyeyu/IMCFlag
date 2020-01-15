package net.imyeyu.imcflag.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class FlagListener implements Listener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getMaterial() == Material.BLAZE_ROD) {
			Action action = event.getAction();
			if (action.equals(Action.LEFT_CLICK_BLOCK)) {
				// 点击位置
				Location pos = event.getClickedBlock().getLocation();
				// 偏移中心
				pos = centerPos(pos);
				// 设置烈焰棒信息
				ItemMeta data = event.getItem().getItemMeta();
				event.getItem().setItemMeta(setTpLore(data, pos, event.getPlayer().getName()));
				event.getPlayer().sendTitle("", "标记 : " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ(), 10, 20, 10);
				// 阻止默认事件
				event.setCancelled(true);
				return;
			}
			if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
				ItemMeta data = event.getItem().getItemMeta();
				String worldName = data.getLocalizedName();
				if (worldName != null && !worldName.equals("")) {
					event.getPlayer().teleport(getTpLocation(data.getLore(), worldName));
				}
			}
		}
	}
	
	private Location getTpLocation(List<String> lore, String worldName) {
		World world = Bukkit.getWorld(worldName);
		String[] poss = lore.get(1).substring(4).split(",");
		double x = Double.parseDouble(poss[0].trim());
		double y = Double.parseDouble(poss[1].trim());
		double z = Double.parseDouble(poss[2].trim());
		return new Location(world, x, y, z);
	}
	
	private ItemMeta setTpLore(ItemMeta data, Location pos, String playerName) {
		String world;
		switch (pos.getWorld().getName()) {
			case "world_the_end":
				world = "末影之地";
				break;
			case "world_nether":
				world = "下届地狱";
				break;
			default:
				world = "主世界";
				break;
		}
		List<String> lore = new ArrayList<String>();
		lore.add("世界: " + world);
		lore.add("坐标: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
		lore.add("来自: " + playerName);
		lore.add("");
		lore.add("使用方式：");
		lore.add("    左键点击方块重新标记（恢复命名）");
		lore.add("    右键传送（无消耗）");
		data.setDisplayName("传送棒");
		data.setLocalizedName(pos.getWorld().getName());
		data.setLore(lore);
		return data;
	}
	
	private Location centerPos(Location pos) {
		pos.setX(pos.getX() + .5);
		pos.setY(pos.getY() + 1);
		pos.setZ(pos.getZ() + .5);
		return pos;
	}
}