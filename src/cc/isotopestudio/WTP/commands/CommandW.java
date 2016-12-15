/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.commands;

import cc.isotopestudio.WTP.WTP;
import cc.isotopestudio.WTP.files.WTPPlayers;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandW implements CommandExecutor {
    private final WTPPlayers wtpPlayers;

    public CommandW(WTP plugin) {
        wtpPlayers = new WTPPlayers(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("w")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("WTP.teleport")) {
                    sender.sendMessage(String.valueOf(ChatColor.RED) + "��û��Ȩ�޴���");
                    return true;
                }
                if (args != null && args.length == 1) {
                    wtpPlayers.tpWarp(player, args[0]);
                    return true;
                } else {
                    sender.sendMessage(String.valueOf(ChatColor.RED) + "/w <�ر�����>" +
                            ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE +
                            "���͵������ر꣬����/wlist�鿴�ر��б�");
                    return true;
                }
            } else {
                sender.sendMessage(
                        (new StringBuilder(WTP.prefix)).append(ChatColor.RED).append("ֻ�������ִ��������").toString());
                return true;
            }
        }
        return false;
    }
}
