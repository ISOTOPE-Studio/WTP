/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.commands;

import cc.isotopestudio.WTP.WTP;
import cc.isotopestudio.WTP.data.WTPConfig;
import cc.isotopestudio.WTP.data.WTPData;
import cc.isotopestudio.WTP.data.WTPPlayers;
import cc.isotopestudio.WTP.util.S;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static cc.isotopestudio.WTP.WTP.plugin;

public class CommandWtpadmin implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("wtpadmin")) {
            if (!sender.hasPermission("WTP.admin")) {
                sender.sendMessage(S.toPrefixRed("你没有权限"));
                return true;
            }
            if (args.length > 0 && !args[0].equals("help")) {
                if (args[0].equals("check") && args.length == 2) {
                    sender.sendMessage(args[1] + ": " + WTPConfig.getLimit(args[1]));
                }

                if (args[0].equalsIgnoreCase("player")) {
                    if (args.length == 2) {
                        Player player = Bukkit.getServer().getPlayer(args[1]);
                        String playerName = args[1];
                        // Check player online
                        if (player != null) {
                            playerName = player.getName();
                        }
                        sender.sendMessage((new StringBuilder(WTP.prefix)).append(ChatColor.AQUA).append("玩家")
                                .append(args[1]).append("的公共地标").toString());
                        if (player != null)
                            sender.sendMessage((new StringBuilder("    ")).append(ChatColor.YELLOW)
                                    .append(ChatColor.ITALIC).append("还可以拥有").append(WTPPlayers.getPlayerSpareString(player)).append("个，已拥有").append(WTPPlayers.getPlayerWarpNum(player)).append("个公共地标")
                                    .toString());

                        List<String> warpsList = WTPPlayers.getPlayerWarpsList(playerName);
                        for (String aWarpsList : warpsList) {
                            sender.sendMessage(WTPData.getWarpDes(aWarpsList));
                        }
                        return true;
                    } else {
                        sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("/wtpadmin player <玩家名字>")
                                .append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE).append("查看玩家的地标信息")
                                .toString());
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("warp")) {
                    if (args.length == 2) {
                        if (!WTPData.ifWarpExist(args[1])) {
                            sender.sendMessage(S.toPrefixRed("地标不存在"));
                            return true;
                        }
                        sender.sendMessage((new StringBuilder(WTP.prefix)).append(ChatColor.AQUA).append("地标: ")
                                .append(args[1]).toString());
                        sender.sendMessage(WTPData.getWarpDes(args[1]));
                        sender.sendMessage((new StringBuilder("    ")).append(ChatColor.AQUA).append("拥有着: ")
                                .append(WTPData.getOwner(args[1])).toString());
                        return true;
                    } else {
                        sender.sendMessage(S.toPrefixRed("/wtpadmin warp <地标名字>" +
                                ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "查看公共地标信息"));

                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("delete")) {
                    if (args.length == 2) {
                        if (!WTPData.ifWarpExist(args[1])) {
                            sender.sendMessage(S.toPrefixRed("地标不存在"));
                            return true;
                        }
                        WTPData.deleteWarp(args[1]);
                        sender.sendMessage(
                                String.valueOf(ChatColor.AQUA) + "成功删除" + args[1]);
                        return true;
                    } else {
                        sender.sendMessage(S.toPrefixRed("/wtpadmin delete <地标名字>" +
                                ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "删除一个公共地标"));
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("about")) {
                    CommandsUti.about(sender);
                    return true;
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    plugin.onReload();
                    sender.sendMessage(
                            S.toPrefixGreen("成功重载"));
                    return true;
                } else {
                    sender.sendMessage(
                            S.toPrefixRed("未知命令，输入/wtpadmin查看帮助"));
                    return true;
                }
            } else { // Help Menu
                sender.sendMessage(
                        (new StringBuilder(WTP.prefix)).append(ChatColor.AQUA).append("== 管理员菜单 ==").toString());
                sender.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append("/wtpadmin player <玩家名字>")
                        .append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE).append("查看玩家的地标信息")
                        .toString());
                sender.sendMessage(String.valueOf(ChatColor.GREEN) + "/wtpadmin warp <地标名字>" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "查看公共地标信息");
                sender.sendMessage(String.valueOf(ChatColor.GREEN) + "/wtpadmin delete <地标名字>" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "删除一个公共地标");
                sender.sendMessage(String.valueOf(ChatColor.GREEN) + "/wtpadmin reload" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "重载插件");
                sender.sendMessage(
                        String.valueOf(ChatColor.GREEN) + "/wtpadmin about" + ChatColor.GRAY +
                                " - " + ChatColor.LIGHT_PURPLE + "查看插件信息");

                return true;
            }
        }
        return false;
    }
}
