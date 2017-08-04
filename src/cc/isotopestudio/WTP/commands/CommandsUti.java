/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.commands;

import cc.isotopestudio.WTP.WTP;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

class CommandsUti {

    static void about(CommandSender sender) {
        sender.sendMessage((new StringBuilder()).append(ChatColor.GRAY).append("---- ").append(WTP.prefix)
                .append(ChatColor.RESET).append(ChatColor.DARK_GRAY).append(" " + WTP.version).append(ChatColor.GRAY)
                .append(" ----").toString());
        sender.sendMessage((new StringBuilder()).append(ChatColor.BLUE).append(ChatColor.ITALIC).append("为服务器制作的公共地标插件")
                .toString());
        sender.sendMessage((new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("制作： ")
                .append(ChatColor.RESET).append(ChatColor.AQUA).append("Mars (ISOTOPE Studio)").toString());
        sender.sendMessage((new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("网址： ")
                .append(ChatColor.RESET).append(ChatColor.AQUA).append("http://isotopestudio.cc/minecraft.html")
                .toString());
    }

    static int getNumber(String args, int size) {
        // Get Number
        int redeem = 0;
        ArrayList<Integer> number = new ArrayList<>();
        for (int i = 0; i < args.length(); i++) {
            char temp = args.charAt(i);
            if (temp < '0' || temp > '9') {
                return -1;
            }
            number.add(temp - '0');
        }
        if (size != -1)
            if (number.size() != size) {
                return -1;
            }
        for (int i = number.size() - 1; i >= 0; i--) {
            int digit = number.size() - i - 1;
            redeem += Math.pow(10, digit) * number.get(i);
        }
        return redeem;
    }
}
