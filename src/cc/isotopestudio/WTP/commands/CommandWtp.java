/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.commands;

import cc.isotopestudio.WTP.WTP;
import cc.isotopestudio.WTP.files.WTPConfig;
import cc.isotopestudio.WTP.files.WTPData;
import cc.isotopestudio.WTP.files.WTPPlayers;
import cc.isotopestudio.WTP.util.S;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWtp implements CommandExecutor {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("wtp")) {
            if (args.length > 0 && !args[0].equals("help") && sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("WTP.control")) {
                    sender.sendMessage(S.toPrefixRed("��û��Ȩ��"));
                    return true;
                }
                if (args[0].equals("create")) {
                    if (args.length == 2) {
                        if (WTPPlayers.getPlayerSpare(player) <= 0 && WTPPlayers.getPlayerWarpLim(player) != -1) {
                            sender.sendMessage(S.toPrefixRed("�㲻���ٴ�������ĵر���"));
                            return true;
                        }
                        if (!args[1].matches("^[a-zA-Z]*")) {
                            sender.sendMessage(S.toPrefixRed("����ֻ��ΪӢ����ĸ"));
                            return true;
                        }
                        if (args[1].length() >= 10) {
                            sender.sendMessage(S.toPrefixRed("���ֲ��ܳ���ʮ����ĸ"));
                            return true;
                        }
                        if (WTPData.ifWarpExist(args[1])) {
                            sender.sendMessage(S.toPrefixRed("�ر�" + args[1] + "�Ѿ����ڣ��������ְ�"));
                            return true;
                        }

                        if (WTP.econ.getBalance(player.getName()) < WTPConfig.createFee) {
                            sender.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
                            return true;
                        }
                        WTP.econ.withdrawPlayer(player.getName(), WTPConfig.createFee);
                        WTPData.addWarp(player, args[1]);
                        sender.sendMessage(S.toPrefixGreen("�ɹ�������"));
                        return true;
                    } else {
                        sender.sendMessage(S.toPrefixRed("/wtp create <�ر�����>") + S.toGray(" - ") +
                                S.toLightPurple("����" + WTPConfig.createFee + "����һ�������ر�"));
                        return true;
                    }
                }

                if (args[0].equals("alias")) {
                    if (args.length >= 3) {
                        if (!WTPPlayers.isOwner(player, args[1])) {
                            sender.sendMessage(S.toPrefixRed("�ⲻ����ĵر�"));
                            return true;
                        }
                        String alias = CommandsUti.getArgsString(args, 2);
                        if (alias.length() >= 15) {
                            sender.sendMessage(S.toPrefixRed("�������ܳ���15����"));
                            return true;
                        }
                        if (WTP.econ.getBalance(player.getName()) < WTPConfig.aliasFee) {
                            sender.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
                            return true;
                        }
                        WTP.econ.withdrawPlayer(player.getName(), WTPConfig.aliasFee);
                        WTPData.addAlias(args[1], alias);
                        sender.sendMessage(S.toPrefixGreen("�ɹ���ӱ�����"));
                        return true;
                    } else {
                        sender.sendMessage(S.toPrefixRed("/wtp alias <�ر�����> <�ر����>" +
                                ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "����" + WTPConfig.aliasFee + "�������ر���ӱ���"));
                        return true;
                    }
                }

                if (args[0].equals("msg")) {
                    if (args.length >= 3) {
                        if (!WTPPlayers.isOwner(player, args[1])) {
                            sender.sendMessage(S.toPrefixRed("�ⲻ����ĵر�"));
                            return true;
                        }
                        String msg = CommandsUti.getArgsString(args, 2);
                        if (msg.length() >= 30) {
                            sender.sendMessage(
                                    S.toPrefixRed("��ӭ��Ϣ���ܳ���30����"));
                            return true;
                        }
                        if (WTP.econ.getBalance(player.getName()) < WTPConfig.welcomeFee) {
                            sender.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
                            return true;
                        }
                        WTP.econ.withdrawPlayer(player.getName(), WTPConfig.welcomeFee);
                        WTPData.addMsg(args[1], msg);
                        sender.sendMessage(S.toPrefixGreen("�ɹ���ӻ�ӭ��Ϣ��"));
                        return true;
                    } else {
                        sender.sendMessage(S.toPrefixRed("/wtp msg <�ر�����> <��ӭ��Ϣ>" +
                                ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "����" + WTPConfig.welcomeFee + "�������ر���ӻ�ӭ��Ϣ"));
                        return true;
                    }
                }

                if (args[0].equals("relocate")) {
                    if (args.length == 2) {
                        if (!WTPPlayers.isOwner(player, args[1])) {
                            sender.sendMessage(S.toPrefixRed("�ⲻ����ĵر�"));
                            return true;
                        }
                        if (WTP.econ.getBalance(player.getName()) < WTPConfig.relocationFee) {
                            sender.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
                            return true;
                        }
                        WTP.econ.withdrawPlayer(player.getName(), WTPConfig.relocationFee);
                        WTPData.relocate(args[1], player);
                        sender.sendMessage(S.toPrefixGreen("�ɹ��ı䴫�͵㣡"));
                        return true;
                    } else {
                        sender.sendMessage(S.toPrefixRed("/wtp relocate <�ر�����>" +
                                ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "����" + WTPConfig.relocationFee + "���Ĺ����ر�Ϊ��ǰλ��"));
                        return true;
                    }
                }

                if (args[0].equals("about")) {
                    CommandsUti.about(sender);
                    return true;
                } else {
                    sender.sendMessage(S.toPrefixRed("δ֪�������/wtp�鿴����"));
                    return true;
                }
            } else { // Help Menu
                sender.sendMessage(
                        (new StringBuilder(WTP.prefix)).append(ChatColor.AQUA).append("== ���� ==").toString());
                sender.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("����һ�����Դ��������ر�Ĳ����������ҿ����������")
                        .append(ChatColor.RED).append(ChatColor.ITALIC).append("�ر�����").append(ChatColor.RESET)
                        .append(ChatColor.YELLOW).append("���͹���").toString());
                sender.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("�鿴�ر��б����ʱ��ҽ��ῴ�������õĵر�")
                        .append(ChatColor.RED).append(ChatColor.ITALIC).append("��ӭ��Ϣ").toString());
                sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append(ChatColor.ITALIC).append("�ر����")
                        .append(ChatColor.RESET).append(ChatColor.YELLOW).append("��������ҿ����ر�ļ�����Ľ���").toString());
                if (sender instanceof Player)
                    sender.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append("�㻹���Դ���").append(WTPPlayers.getPlayerSpareString((Player) sender)).append("���ر�").toString());
                sender.sendMessage(
                        (new StringBuilder(WTP.prefix)).append(ChatColor.AQUA).append("== ����˵� ==").toString());
                sender.sendMessage(String.valueOf(ChatColor.GREEN) + "/w <�ر�����>" + ChatColor.GRAY +
                        " - " + ChatColor.LIGHT_PURPLE + "���͵������ر�");
                sender.sendMessage(
                        String.valueOf(ChatColor.GREEN) + "/wlist [ҳ��]" + ChatColor.GRAY +
                                " - " + ChatColor.LIGHT_PURPLE + "�鿴�����ر��б�");

                sender.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append("/wtp create <�ر�����>")
                        .append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE).append("����").append(WTPConfig.createFee).append("����һ�������ر�").toString());
                sender.sendMessage(String.valueOf(ChatColor.GREEN) + "/wtp alias <�ر�����> <�ر����>" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "����" + WTPConfig.aliasFee + "�������ر���ӱ���");
                sender.sendMessage(String.valueOf(ChatColor.GREEN) + "/wtp msg <�ر�����> <��ӭ��Ϣ>" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "����" + WTPConfig.welcomeFee + "�������ر���ӻ�ӭ��Ϣ");
                sender.sendMessage(String.valueOf(ChatColor.GREEN) + "/wtp relocate <�ر�����>" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "����" + WTPConfig.relocationFee + "���Ĺ����ر�Ϊ��ǰλ��");
                sender.sendMessage(
                        String.valueOf(ChatColor.GREEN) + "/wtp about" + ChatColor.GRAY +
                                " - " + ChatColor.LIGHT_PURPLE + "�鿴�����Ϣ");

                if (!(sender instanceof Player)) {
                    sender.sendMessage(S.toPrefixRed("ֻ�������ִ����Щ���"));
                }
                return true;
            }
        }
        return false;
    }
}
