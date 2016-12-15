/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.commands;

import cc.isotopestudio.WTP.files.WTPPlayers;
import cc.isotopestudio.WTP.WTP;
import cc.isotopestudio.WTP.files.WTPConfig;
import cc.isotopestudio.WTP.files.WTPData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWtp implements CommandExecutor {
    private final WTPData wtpData;
    private final WTPPlayers wtpPlayers;

    public CommandWtp(WTP plugin) {
        wtpData = new WTPData(plugin);
        wtpPlayers = new WTPPlayers(plugin);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("wtp")) {
            if (args.length > 0 && !args[0].equals("help") && sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("WTP.control")) {
                    sender.sendMessage(String.valueOf(ChatColor.RED) + "��û��Ȩ��");
                    return true;
                }
                if (args[0].equals("create")) {
                    if (args.length == 2) {
                        if (wtpPlayers.getPlayerSpare(player) <= 0 && wtpPlayers.getPlayerWarpLim(player) != -1) {
                            sender.sendMessage(
                                    String.valueOf(ChatColor.RED) + "�㲻���ٴ�������ĵر���");
                            return true;
                        }
                        if (!args[1].matches("^[a-zA-Z]*")) {
                            sender.sendMessage(
                                    String.valueOf(ChatColor.RED) + "����ֻ��ΪӢ����ĸ");
                            return true;
                        }
                        if (args[1].length() >= 10) {
                            sender.sendMessage(
                                    String.valueOf(ChatColor.RED) + "���ֲ��ܳ���ʮ����ĸ");
                            return true;
                        }
                        if (wtpData.ifWarpExist(args[1])) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "�ر�" + args[1] + "�Ѿ����ڣ��������ְ�");
                            return true;
                        }

                        if (WTP.econ.getBalance(player.getName()) < WTPConfig.createFee) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "��Ľ�Ǯ����");
                            return true;
                        }
                        WTP.econ.withdrawPlayer(player.getName(), WTPConfig.createFee);
                        wtpData.addWarp(player, args[1]);
                        sender.sendMessage(String.valueOf(ChatColor.AQUA) + "�ɹ�������");
                        return true;
                    } else {
                        sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("/wtp create <�ر�����>")
                                .append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE).append("����").append(WTPConfig.createFee).append("����һ�������ر�").toString());
                        return true;
                    }
                }

                if (args[0].equals("alias")) {
                    if (args.length >= 3) {
                        if (!wtpPlayers.isOwner(player, args[1])) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "�ⲻ����ĵر�");
                            return true;
                        }
                        String alias = CommandsUti.getArgsString(args, 2);
                        if (alias.length() >= 15) {
                            sender.sendMessage(
                                    String.valueOf(ChatColor.RED) + "�������ܳ���15����");
                            return true;
                        }
                        if (WTP.econ.getBalance(player.getName()) < WTPConfig.aliasFee) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "��Ľ�Ǯ����");
                            return true;
                        }
                        WTP.econ.withdrawPlayer(player.getName(), WTPConfig.aliasFee);
                        wtpData.addAlias(args[1], alias);
                        sender.sendMessage(String.valueOf(ChatColor.AQUA) + "�ɹ���ӱ�����");
                        return true;
                    } else {
                        sender.sendMessage(String.valueOf(ChatColor.RED) + "/wtp alias <�ر�����> <�ر����>" +
                                ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "����" + WTPConfig.aliasFee + "�������ر���ӱ���");
                        return true;
                    }
                }

                if (args[0].equals("msg")) {
                    if (args.length >= 3) {
                        if (!wtpPlayers.isOwner(player, args[1])) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "�ⲻ����ĵر�");
                            return true;
                        }
                        String msg = CommandsUti.getArgsString(args, 2);
                        if (msg.length() >= 30) {
                            sender.sendMessage(
                                    String.valueOf(ChatColor.RED) + "��ӭ��Ϣ���ܳ���30����");
                            return true;
                        }
                        if (WTP.econ.getBalance(player.getName()) < WTPConfig.welcomeFee) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "��Ľ�Ǯ����");
                            return true;
                        }
                        WTP.econ.withdrawPlayer(player.getName(), WTPConfig.welcomeFee);
                        wtpData.addMsg(args[1], msg);
                        sender.sendMessage(String.valueOf(ChatColor.AQUA) + "�ɹ���ӻ�ӭ��Ϣ��");
                        return true;
                    } else {
                        sender.sendMessage(String.valueOf(ChatColor.RED) + "/wtp msg <�ر�����> <��ӭ��Ϣ>" +
                                ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "����" + WTPConfig.welcomeFee + "�������ر���ӻ�ӭ��Ϣ");
                        return true;
                    }
                }

                if (args[0].equals("relocate")) {
                    if (args.length == 2) {
                        if (!wtpPlayers.isOwner(player, args[1])) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "�ⲻ����ĵر�");
                            return true;
                        }
                        if (WTP.econ.getBalance(player.getName()) < WTPConfig.relocationFee) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "��Ľ�Ǯ����");
                            return true;
                        }
                        WTP.econ.withdrawPlayer(player.getName(), WTPConfig.relocationFee);
                        wtpData.relocate(args[1], player);
                        sender.sendMessage(String.valueOf(ChatColor.AQUA) + "�ɹ��ı䴫�͵㣡");
                        return true;
                    } else {
                        sender.sendMessage(String.valueOf(ChatColor.RED) + "/wtp relocate <�ر�����>" +
                                ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "����" + WTPConfig.relocationFee + "���Ĺ����ر�Ϊ��ǰλ��");
                        return true;
                    }
                }

                if (args[0].equals("about")) {
                    CommandsUti.about(sender);
                    return true;
                } else {
                    sender.sendMessage(String.valueOf(ChatColor.RED) + "δ֪�������/wtp�鿴����");
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
                    sender.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append("�㻹���Դ���").append(wtpPlayers.getPlayerSpareString((Player) sender)).append("���ر�").toString());
                sender.sendMessage(
                        (new StringBuilder(WTP.prefix)).append(ChatColor.AQUA).append("== ����˵� ==").toString());
                sender.sendMessage(String.valueOf(ChatColor.GOLD) + "/w <�ر�����>" + ChatColor.GRAY +
                        " - " + ChatColor.LIGHT_PURPLE + "���͵������ر�");
                sender.sendMessage(
                        String.valueOf(ChatColor.GOLD) + "/wlist [ҳ��]" + ChatColor.GRAY +
                                " - " + ChatColor.LIGHT_PURPLE + "�鿴�����ر��б�");

                sender.sendMessage((new StringBuilder()).append(ChatColor.GOLD).append("/wtp create <�ر�����>")
                        .append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE).append("����").append(WTPConfig.createFee).append("����һ�������ر�").toString());
                sender.sendMessage(String.valueOf(ChatColor.GOLD) + "/wtp alias <�ر�����> <�ر����>" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "����" + WTPConfig.aliasFee + "�������ر���ӱ���");
                sender.sendMessage(String.valueOf(ChatColor.GOLD) + "/wtp msg <�ر�����> <��ӭ��Ϣ>" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "����" + WTPConfig.welcomeFee + "�������ر���ӻ�ӭ��Ϣ");
                sender.sendMessage(String.valueOf(ChatColor.GOLD) + "/wtp relocate <�ر�����>" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "����" + WTPConfig.relocationFee + "���Ĺ����ر�Ϊ��ǰλ��");
                sender.sendMessage(
                        String.valueOf(ChatColor.GOLD) + "/wtp about" + ChatColor.GRAY +
                                " - " + ChatColor.LIGHT_PURPLE + "�鿴�����Ϣ");

                if (!(sender instanceof Player)) {
                    sender.sendMessage(
                            (new StringBuilder(WTP.prefix)).append(ChatColor.RED).append("ֻ�������ִ����Щ���").toString());
                }
                return true;
            }
        }
        return false;
    }
}
