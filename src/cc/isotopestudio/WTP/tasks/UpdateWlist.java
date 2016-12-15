/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.tasks;

import cc.isotopestudio.WTP.WTP;
import cc.isotopestudio.WTP.files.WTPData;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class UpdateWlist extends BukkitRunnable {
    private final WTP plugin;

    public UpdateWlist(WTP plugin) {
        this.plugin = plugin;
    }

    public static void updateWlist(WTP plugin) {
        Set<String> warpSet = plugin.getWarpsData().getKeys(false);
        if (warpSet.size() != 0) {
            List<String> warpList = new ArrayList<>();

            warpList.addAll(warpSet);
            Collections.shuffle(warpList);

            // plugin.getLogger().info(warpList.toString());

            String[][] listMsg;
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
                    lines = lines >= 1 ? 7 : amount % 7;
                    for (int line = 1; line <= lines; line++) {

                        int index = page * 7 + (line - 1);

                        StringBuilder msg = new StringBuilder(" [" + (index + 1) + "]  ").append(ChatColor.GOLD);
                        String name = warpList.get(index);
                        String alias = WTPData.getAlias(name, plugin);
                        if (alias != null) {
                            msg.append(alias);
                        }
                        msg.append(ChatColor.GRAY).append("(").append(name).append(")");
                        String welcome = WTPData.getMsg(name, plugin);
                        if (welcome != null) {
                            msg.append(ChatColor.AQUA).append(" - ").append(welcome);
                        }
                        listMsg[page][line] = msg.toString();
                    }
                    listMsg[page][8] = (new StringBuilder(WTP.prefix).append(ChatColor.AQUA).append("第").append(page + 1).append("页，共").append(pages).append("页")).toString();
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
