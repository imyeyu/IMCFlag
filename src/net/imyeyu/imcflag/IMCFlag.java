package net.imyeyu.imcflag;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.imyeyu.imcflag.listener.FlagListener;

public class IMCFlag extends JavaPlugin {
	
	public void onEnable() {
		// 注册监听器
		Bukkit.getPluginManager().registerEvents(new FlagListener(), this);
	}
}