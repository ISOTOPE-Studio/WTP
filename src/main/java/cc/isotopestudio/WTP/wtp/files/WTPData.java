package cc.isotopestudio.WTP.wtp.files;

import java.util.List;

import org.bukkit.Bukkit;
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
		List<String> warpList = plugin.getPlayersData().getStringList(player.getName());
		warpList.add(name);
		plugin.getPlayersData().set(player.getName() + ".warps", warpList);
		plugin.savePlayersData();
		plugin.saveWarpsData();
	}

	public void tpWarp(Player player, String name) {
		World world = Bukkit.getServer().getWorld(plugin.getWarpsData().getString(name + ".world"));
		Location loc = new Location(world, plugin.getWarpsData().getDouble(name + ".X"),
				plugin.getWarpsData().getDouble(name + ".Y"), plugin.getWarpsData().getDouble(name + ".Z"));
		player.teleport(loc);
	}

}
