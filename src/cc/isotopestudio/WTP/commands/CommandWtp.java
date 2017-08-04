/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.commands;

import cc.isotopestudio.WTP.WTP;
import cc.isotopestudio.WTP.data.WTPPlayers;
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
//                Player player = (Player) sender;
//                if (!player.hasPermission("WTP.control")) {
//                    sender.sendMessage(S.toPrefixRed("��û��Ȩ��"));
//                    return true;
//                }

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
                sender.sendMessage(S.toGreen("/w") + S.toGray(" - ") + S.toLightPurple("��WTP�˵� (�ҵ��ղ� �� �ҵĵر�)"));
                sender.sendMessage(String.valueOf(ChatColor.GREEN) + "/w <�ر�����>" + ChatColor.GRAY +
                        " - " + ChatColor.LIGHT_PURPLE + "���͵������ر�");
                sender.sendMessage(
                        String.valueOf(ChatColor.GREEN) + "/wlist" + ChatColor.GRAY +
                                " - " + ChatColor.LIGHT_PURPLE + "�鿴�����������ر�");
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
