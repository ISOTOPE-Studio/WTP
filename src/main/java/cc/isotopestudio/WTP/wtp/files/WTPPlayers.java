package cc.isotopestudio.WTP.wtp.files;

import java.util.List;

import org.bukkit.entity.Player;

import cc.isotopestudio.WTP.wtp.WTP;

public class WTPPlayers {

	public static int returnPlayerSpare(Player player, WTP plugin) {
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
		List<String> warpsList = plugin.getPlayersData().getStringList(player.getName() + ".warps");
		player.sendMessage(limit+" "+warpsList.size());
		return limit - warpsList.size();
	}

	public static String returnPlayerSpareString(Player player, WTP plugin) {
		int limit = returnPlayerSpare(player, plugin);
		if (limit >= 0)
			return limit + "";
		else
			return "нчоч";
	}

}
