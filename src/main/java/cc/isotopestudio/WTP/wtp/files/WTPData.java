package cc.isotopestudio.WTP.wtp.files;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import cc.isotopestudio.WTP.wtp.WTP;

public class WTPData {

	private final WTP plugin;

	public WTPData(WTP plugin) {
		this.plugin = plugin;
	}

	public void addWarp(Player player, String name) {
		plugin.getWarpsData().set(name + ".owner", player.getName());
		Location loc = player.getLocation();
		plugin.getWarpsData().set(name + ".world", loc.getWorld().getName());
		plugin.getWarpsData().set(name + ".X", loc.getX());
		plugin.getWarpsData().set(name + ".Y", loc.getY());
		plugin.getWarpsData().set(name + ".Z", loc.getZ());
		plugin.getWarpsData().set(name + ".pitch", loc.getPitch());
		plugin.getWarpsData().set(name + ".yaw", loc.getYaw());
		List<String> warpList = plugin.getPlayersData().getStringList(player.getName() + ".warps");
		warpList.add(name);
		plugin.getPlayersData().set(player.getName() + ".warps", warpList);
		plugin.savePlayersData();
		plugin.saveWarpsData();
	}

	public boolean ifWarpExist(String name) {
		Set<String> list = plugin.getWarpsData().getKeys(false);
		for (String temp : list) {
			if (temp.equals(name))
				return true;
		}
		return false;
	}

	public void addAlias(String name, String alias) {
		plugin.getWarpsData().set(name + ".alias", alias);
		plugin.saveWarpsData();
	}

	public void addMsg(String name, String alias) {
		plugin.getWarpsData().set(name + ".welcome", alias);
		plugin.saveWarpsData();
	}

	public void relocate(String name, Player player) {
		Location loc = player.getLocation();
		plugin.getWarpsData().set(name + ".world", loc.getWorld().getName());
		plugin.getWarpsData().set(name + ".X", loc.getX());
		plugin.getWarpsData().set(name + ".Y", loc.getY());
		plugin.getWarpsData().set(name + ".Z", loc.getZ());
		plugin.getWarpsData().set(name + ".pitch", loc.getPitch());
		plugin.getWarpsData().set(name + ".yaw", loc.getYaw());
		plugin.saveWarpsData();
	}
}
