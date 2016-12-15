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
                    sender.sendMessage(String.valueOf(ChatColor.RED) + "你没有权限传送");
                    return true;
                }
                if (args != null && args.length == 1) {
                    wtpPlayers.tpWarp(player, args[0]);
                    return true;
                } else {
                    sender.sendMessage(String.valueOf(ChatColor.RED) + "/w <地标名字>" +
                            ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE +
                            "传送到公共地标，输入/wlist查看地标列表");
                    return true;
                }
            } else {
                sender.sendMessage(
                        (new StringBuilder(WTP.prefix)).append(ChatColor.RED).append("只有玩家能执行这个命令！").toString());
                return true;
            }
        }
        return false;
    }
}
