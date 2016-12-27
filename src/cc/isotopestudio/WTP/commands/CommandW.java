/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.commands;

import cc.isotopestudio.WTP.WTP;
import cc.isotopestudio.WTP.files.WTPPlayers;
import cc.isotopestudio.WTP.gui.FavoriteGUI;
import cc.isotopestudio.WTP.gui.WarpGUI;
import cc.isotopestudio.WTP.util.S;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandW implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("w")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("WTP.teleport")) {
                    sender.sendMessage(S.toPrefixRed("��û��Ȩ�޴���"));
                    return true;
                }
                new FavoriteGUI(player).open(player);
                if (args != null && args.length == 1) {
                    WTPPlayers.tpWarp(player, args[0]);
                    return true;
                } else {
                    sender.sendMessage(S.toPrefixRed("/w <�ر�����>") +
                            ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE +
                            "���͵������ر꣬����/wlist�鿴�ر��б�");
                    return true;
                }
            } else {
                sender.sendMessage(S.toPrefixRed("ֻ�������ִ��������"));
                return true;
            }
        }
        return false;
    }
}
