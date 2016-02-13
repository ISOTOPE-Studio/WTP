package cc.isotopestudio.WTP.wtp.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import cc.isotopestudio.WTP.wtp.WTP;
import cc.isotopestudio.WTP.wtp.files.WTPData;

public class UpdateWlist extends BukkitRunnable {
	private final WTP plugin;

	public UpdateWlist(WTP plugin) {
		this.plugin = plugin;
	}

	public static void updateWlist(WTP plugin) {
		Set<String> warpSet = plugin.getWarpsData().getKeys(false);
		if (warpSet.size() != 0) {
			List<String> warpList = new ArrayList<String>();

			Iterator<String> it = warpSet.iterator();
			while (it.hasNext()) {
				warpList.add(it.next());
			}
			Collections.shuffle(warpList);

			// plugin.getLogger().info(warpList.toString());

			String[][] listMsg = null;
			if (warpList.size() != 0) {
				int amount = warpList.size();
				int pages = amount / 7;
				if (amount % 7 != 0)
					pages++;
				plugin.commandWlist.pages = pages;
				listMsg = new String[pages][9];
				for (int page = 0; page < pages; page++) {
					listMsg[page][0] = (new StringBuilder(WTP.prefix).append(ChatColor.AQUA).append("=== 地标列表 ==="))
							.toString();
					int lines = (amount - (page * 7)) / 7;
					if (lines >= 1)
						lines = 7;
					else
						lines = amount % 7;
					for (int line = 1; line <= lines; line++) {

						int index = page * 7 + (line - 1);

						StringBuilder msg = new StringBuilder(" [" + (index + 1) + "]  ").append(ChatColor.GOLD);
						String name = warpList.get(index);
						String alias = WTPData.getAlias(name, plugin);
						if (alias != null) {
							msg.append(alias);
						}
						msg.append(ChatColor.GRAY).append("(" + name + ")");
						String welcome = WTPData.getMsg(name, plugin);
						if (welcome != null) {
							msg.append(ChatColor.AQUA).append(" - " + welcome);
						}
						listMsg[page][line] = msg.toString();
					}
					listMsg[page][8] = (new StringBuilder(WTP.prefix).append(ChatColor.AQUA)
							.append("第" + (page + 1) + "页，共" + pages + "页")).toString();
				}
				plugin.commandWlist.warpListMsg = listMsg;
			}
		} else {
			plugin.commandWlist.pages = 0;
			plugin.commandWlist.warpListMsg = null;
		}
	}

	@Override
	public void run() {
		updateWlist(plugin);
	}

}
