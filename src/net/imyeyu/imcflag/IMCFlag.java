package net.imyeyu.imcflag;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.imyeyu.imcflag.listener.FlagListener;

public class IMCFlag extends JavaPlugin {
	
	public static Logger log;
	
	public void onEnable() {
		log = getLogger();
		Bukkit.getPluginManager().registerEvents(new FlagListener(), this);
	}
}