package cc.isotopestudio.WTP.wtp.files;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import cc.isotopestudio.WTP.wtp.WTP;

public class WTPPlayers {
	private final WTP plugin;
	private final WTPData wtpData;

	public WTPPlayers(WTP plugin) {
		this.plugin = plugin;
		wtpData = new WTPData(plugin);
	}

	public void tpWarp(Player player, String name) {
		if (plugin.getWarpsData().isSet(name)) {
			World world = Bukkit.getServer().getWorld(plugin.getWarpsData().getString(name + ".world"));
			Location loc = new Location(world, plugin.getWarpsData().getDouble(name + ".X"),
					plugin.getWarpsData().getDouble(name + ".Y"), plugin.getWarpsData().getDouble(name + ".Z"),
					(float) plugin.getWarpsData().getDouble(name + ".yaw"),
					(float) plugin.getWarpsData().getDouble(name + ".pitch"));
			player.teleport(loc);
			StringBuilder msg = new StringBuilder(WTP.prefix);
			String alias = wtpData.getAlias(name);
			String welcome = wtpData.getMsg(name);
			if (alias != null)
				msg.append(ChatColor.GOLD).append(alias + ": ");
			if (msg != null)
				msg.append(ChatColor.AQUA).append(welcome);
			player.sendMessage(msg.toString());
		} else {
			player.sendMessage(new StringBuilder(WTP.prefix).append(ChatColor.RED).append("地标不存在").toString());

		}
	}

	public int getPlayerWarpNum(Player player) {
		List<String> warpsList = plugin.getPlayersData().getStringList(player.getName() + ".warps");
		return warpsList.size();
	}

	public int getPlayerWarpLim(Player player) {
		int limit = WTPConfig.getLimit(0);
		if (player.isOp()) {
			limit = WTPConfig.getLimit(6);
		} else
			for (int i = 5; i >= 1; i--) {
				if (player.hasPermission("WTP.create.vip" + i)) {
					limit = WTPConfig.getLimit(i);
					break;
				}
			}
		return limit;
	}

	public int getPlayerSpare(Player player) {
		return getPlayerWarpLim(player) - getPlayerWarpNum(player);
	}

	public String getPlayerSpareString(Player player) {
		if (getPlayerWarpLim(player) != -1) {
			int limit = getPlayerSpare(player);
			return limit + "";
		} else
			return "无限";
	}

	public boolean isOwner(Player player, String name) {
		String owner = plugin.getWarpsData().getString(name + ".owner");
		if (owner != null && owner.equals(player.getName()))
			return true;
		else
			return false;
	}

}
