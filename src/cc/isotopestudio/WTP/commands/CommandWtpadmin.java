/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.commands;

import cc.isotopestudio.WTP.WTP;
import cc.isotopestudio.WTP.files.WTPPlayers;
import cc.isotopestudio.WTP.files.WTPConfig;
import cc.isotopestudio.WTP.files.WTPData;
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
                sender.sendMessage(String.valueOf(ChatColor.RED) + "��û��Ȩ��");
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
                        sender.sendMessage((new StringBuilder(WTP.prefix)).append(ChatColor.AQUA).append("���")
                                .append(args[1]).append("�Ĺ����ر�").toString());
                        if (player != null)
                            sender.sendMessage((new StringBuilder("    ")).append(ChatColor.YELLOW)
                                    .append(ChatColor.ITALIC).append("������ӵ��").append(WTPPlayers.getPlayerSpareString(player)).append("������ӵ��").append(WTPPlayers.getPlayerWarpNum(player)).append("�������ر�")
                                    .toString());

                        List<String> warpsList = WTPPlayers.getPlayerWarpsList(playerName);
                        for (String aWarpsList : warpsList) {
                            sender.sendMessage(WTPData.getWarpDes(aWarpsList));
                        }
                        return true;
                    } else {
                        sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("/wtpadmin player <�������>")
                                .append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE).append("�鿴��ҵĵر���Ϣ")
                                .toString());
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("warp")) {
                    if (args.length == 2) {
                        if (!WTPData.ifWarpExist(args[1])) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "�ر겻����");
                            return true;
                        }
                        sender.sendMessage((new StringBuilder(WTP.prefix)).append(ChatColor.AQUA).append("�ر�: ")
                                .append(args[1]).toString());
                        sender.sendMessage(WTPData.getWarpDes(args[1]));
                        sender.sendMessage((new StringBuilder("    ")).append(ChatColor.AQUA).append("ӵ����: ")
                                .append(WTPData.getOwner(args[1])).toString());
                        return true;
                    } else {
                        sender.sendMessage(String.valueOf(ChatColor.RED) + "/wtpadmin warp <�ر�����>" +
                                ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "�鿴�����ر���Ϣ");

                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("delete")) {
                    if (args.length == 2) {
                        if (!WTPData.ifWarpExist(args[1])) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "�ر겻����");
                            return true;
                        }
                        WTPData.deleteWarp(args[1]);
                        sender.sendMessage(
                                String.valueOf(ChatColor.AQUA) + "�ɹ�ɾ��" + args[1]);
                        return true;
                    } else {
                        sender.sendMessage(String.valueOf(ChatColor.RED) + "/wtpadmin delete <�ر�����>" +
                                ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "ɾ��һ�������ر�");
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("about")) {
                    CommandsUti.about(sender);
                    return true;
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    plugin.onReload();
                    return true;
                } else {
                    sender.sendMessage(
                            String.valueOf(ChatColor.RED) + "δ֪�������/wtpadmin�鿴����");
                    return true;
                }
            } else { // Help Menu
                sender.sendMessage(
                        (new StringBuilder(WTP.prefix)).append(ChatColor.AQUA).append("== ����Ա�˵� ==").toString());
                sender.sendMessage((new StringBuilder()).append(ChatColor.GOLD).append("/wtpadmin player <�������>")
                        .append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE).append("�鿴��ҵĵر���Ϣ")
                        .toString());
                sender.sendMessage(String.valueOf(ChatColor.GOLD) + "/wtpadmin warp <�ر�����>" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "�鿴�����ر���Ϣ");
                sender.sendMessage(String.valueOf(ChatColor.GOLD) + "/wtpadmin delete <�ر�����>" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "ɾ��һ�������ر�");
                sender.sendMessage(String.valueOf(ChatColor.GOLD) + "/wtpadmin reload" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "���ز��");
                sender.sendMessage(
                        String.valueOf(ChatColor.GOLD) + "/wtpadmin about" + ChatColor.GRAY +
                                " - " + ChatColor.LIGHT_PURPLE + "�鿴�����Ϣ");

                return true;
            }
        }
        return false;
    }
}
