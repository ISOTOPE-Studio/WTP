package cc.isotopestudio.WTP.wtp.files;

import java.util.List;

import org.bukkit.entity.Player;

import cc.isotopestudio.WTP.wtp.WTP;

public class WTPPlayers {
	private final WTP plugin;

	public WTPPlayers(WTP plugin) {
		this.plugin = plugin;
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
		}
		else
			return "нчоч";
	}

}
