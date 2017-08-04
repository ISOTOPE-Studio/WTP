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
//                    sender.sendMessage(S.toPrefixRed("你没有权限"));
//                    return true;
//                }

                if (args[0].equals("about")) {
                    CommandsUti.about(sender);
                    return true;
                } else {
                    sender.sendMessage(S.toPrefixRed("未知命令，输入/wtp查看帮助"));
                    return true;
                }
            } else { // Help Menu
                sender.sendMessage(
                        (new StringBuilder(WTP.prefix)).append(ChatColor.AQUA).append("== 帮助 ==").toString());
                sender.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("这是一个可以创建公共地标的插件，其他玩家可以输入你的")
                        .append(ChatColor.RED).append(ChatColor.ITALIC).append("地标名字").append(ChatColor.RESET)
                        .append(ChatColor.YELLOW).append("传送过来").toString());
                sender.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("查看地标列表或传送时玩家将会看到你设置的地标")
                        .append(ChatColor.RED).append(ChatColor.ITALIC).append("欢迎信息").toString());
                sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append(ChatColor.ITALIC).append("地标别名")
                        .append(ChatColor.RESET).append(ChatColor.YELLOW).append("是其他玩家看到地标的简短中文介绍").toString());
                if (sender instanceof Player)
                    sender.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append("你还可以创建").append(WTPPlayers.getPlayerSpareString((Player) sender)).append("个地标").toString());
                sender.sendMessage(
                        (new StringBuilder(WTP.prefix)).append(ChatColor.AQUA).append("== 命令菜单 ==").toString());
                sender.sendMessage(S.toGreen("/w") + S.toGray(" - ") + S.toLightPurple("打开WTP菜单 (我的收藏 和 我的地标)"));
                sender.sendMessage(String.valueOf(ChatColor.GREEN) + "/w <地标名字>" + ChatColor.GRAY +
                        " - " + ChatColor.LIGHT_PURPLE + "传送到公共地标");
                sender.sendMessage(
                        String.valueOf(ChatColor.GREEN) + "/wlist" + ChatColor.GRAY +
                                " - " + ChatColor.LIGHT_PURPLE + "查看服务器公共地标");
                sender.sendMessage(
                        String.valueOf(ChatColor.GREEN) + "/wtp about" + ChatColor.GRAY +
                                " - " + ChatColor.LIGHT_PURPLE + "查看插件信息");

                if (!(sender instanceof Player)) {
                    sender.sendMessage(S.toPrefixRed("只有玩家能执行这些命令！"));
                }
                return true;
            }
        }
        return false;
    }
}
