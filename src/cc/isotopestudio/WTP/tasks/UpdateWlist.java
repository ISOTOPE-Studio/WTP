/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.tasks;

import cc.isotopestudio.WTP.WTP;
import cc.isotopestudio.WTP.files.WTPData;
import cc.isotopestudio.WTP.gui.WarpGUI;
import cc.isotopestudio.WTP.util.S;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cc.isotopestudio.WTP.WTP.warpData;
import static cc.isotopestudio.WTP.gui.WarpGUI.keys;

public class UpdateWlist extends BukkitRunnable {
    private final WTP plugin;

    public UpdateWlist(WTP plugin) {
        this.plugin = plugin;
    }

    public static void updateWlist(WTP plugin) {
        keys = warpData.getKeys(false);
        if (keys.size() != 0) {
            List<String> warpList = new ArrayList<>();

            warpList.addAll(keys);
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
                    listMsg[page][0] = S.toPrefixAqua("=== 地标列表 ===");
                    int lines = (amount - (page * 7)) / 7;
                    lines = lines >= 1 ? 7 : amount % 7;
                    for (int line = 1; line <= lines; line++) {

                        int index = page * 7 + (line - 1);

                        StringBuilder msg = new StringBuilder(" [" + (index + 1) + "]  ").append(ChatColor.GREEN);
                        String name = warpList.get(index);
                        String alias = WTPData.getAlias(name);
                        if (alias != null) {
                            msg.append(alias).append(" ");
                        }
                        msg.append(ChatColor.GRAY).append("(").append(name).append(")");
                        String welcome = WTPData.getMsg(name);
                        if (welcome != null) {
                            msg.append(ChatColor.AQUA).append(" - ").append(welcome);
                        }
                        listMsg[page][line] = msg.toString();
                    }
                    listMsg[page][8] = S.toPrefixAqua("第" + (page + 1) + "页，共" + pages + "页");
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
