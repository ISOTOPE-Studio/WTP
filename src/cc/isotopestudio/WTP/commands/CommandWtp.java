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
                    sender.sendMessage(String.valueOf(ChatColor.RED) + "你没有权限");
                    return true;
                }
                if (args[0].equals("create")) {
                    if (args.length == 2) {
                        if (wtpPlayers.getPlayerSpare(player) <= 0 && wtpPlayers.getPlayerWarpLim(player) != -1) {
                            sender.sendMessage(
                                    String.valueOf(ChatColor.RED) + "你不能再创建更多的地标了");
                            return true;
                        }
                        if (!args[1].matches("^[a-zA-Z]*")) {
                            sender.sendMessage(
                                    String.valueOf(ChatColor.RED) + "名字只能为英文字母");
                            return true;
                        }
                        if (args[1].length() >= 10) {
                            sender.sendMessage(
                                    String.valueOf(ChatColor.RED) + "名字不能超过十个字母");
                            return true;
                        }
                        if (wtpData.ifWarpExist(args[1])) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "地标" + args[1] + "已经存在，换个名字吧");
                            return true;
                        }

                        if (WTP.econ.getBalance(player.getName()) < WTPConfig.createFee) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "你的金钱不足");
                            return true;
                        }
                        WTP.econ.withdrawPlayer(player.getName(), WTPConfig.createFee);
                        wtpData.addWarp(player, args[1]);
                        sender.sendMessage(String.valueOf(ChatColor.AQUA) + "成功创建！");
                        return true;
                    } else {
                        sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("/wtp create <地标名字>")
                                .append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE).append("花费").append(WTPConfig.createFee).append("创建一个公共地标").toString());
                        return true;
                    }
                }

                if (args[0].equals("alias")) {
                    if (args.length >= 3) {
                        if (!wtpPlayers.isOwner(player, args[1])) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "这不是你的地标");
                            return true;
                        }
                        String alias = CommandsUti.getArgsString(args, 2);
                        if (alias.length() >= 15) {
                            sender.sendMessage(
                                    String.valueOf(ChatColor.RED) + "别名不能超过15个字");
                            return true;
                        }
                        if (WTP.econ.getBalance(player.getName()) < WTPConfig.aliasFee) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "你的金钱不足");
                            return true;
                        }
                        WTP.econ.withdrawPlayer(player.getName(), WTPConfig.aliasFee);
                        wtpData.addAlias(args[1], alias);
                        sender.sendMessage(String.valueOf(ChatColor.AQUA) + "成功添加别名！");
                        return true;
                    } else {
                        sender.sendMessage(String.valueOf(ChatColor.RED) + "/wtp alias <地标名字> <地标别名>" +
                                ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "花费" + WTPConfig.aliasFee + "给公共地标添加别名");
                        return true;
                    }
                }

                if (args[0].equals("msg")) {
                    if (args.length >= 3) {
                        if (!wtpPlayers.isOwner(player, args[1])) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "这不是你的地标");
                            return true;
                        }
                        String msg = CommandsUti.getArgsString(args, 2);
                        if (msg.length() >= 30) {
                            sender.sendMessage(
                                    String.valueOf(ChatColor.RED) + "欢迎信息不能超过30个字");
                            return true;
                        }
                        if (WTP.econ.getBalance(player.getName()) < WTPConfig.welcomeFee) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "你的金钱不足");
                            return true;
                        }
                        WTP.econ.withdrawPlayer(player.getName(), WTPConfig.welcomeFee);
                        wtpData.addMsg(args[1], msg);
                        sender.sendMessage(String.valueOf(ChatColor.AQUA) + "成功添加欢迎信息！");
                        return true;
                    } else {
                        sender.sendMessage(String.valueOf(ChatColor.RED) + "/wtp msg <地标名字> <欢迎信息>" +
                                ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "花费" + WTPConfig.welcomeFee + "给公共地标添加欢迎信息");
                        return true;
                    }
                }

                if (args[0].equals("relocate")) {
                    if (args.length == 2) {
                        if (!wtpPlayers.isOwner(player, args[1])) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "这不是你的地标");
                            return true;
                        }
                        if (WTP.econ.getBalance(player.getName()) < WTPConfig.relocationFee) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "你的金钱不足");
                            return true;
                        }
                        WTP.econ.withdrawPlayer(player.getName(), WTPConfig.relocationFee);
                        wtpData.relocate(args[1], player);
                        sender.sendMessage(String.valueOf(ChatColor.AQUA) + "成功改变传送点！");
                        return true;
                    } else {
                        sender.sendMessage(String.valueOf(ChatColor.RED) + "/wtp relocate <地标名字>" +
                                ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "花费" + WTPConfig.relocationFee + "更改公共地标为当前位置");
                        return true;
                    }
                }

                if (args[0].equals("about")) {
                    CommandsUti.about(sender);
                    return true;
                } else {
                    sender.sendMessage(String.valueOf(ChatColor.RED) + "未知命令，输入/wtp查看帮助");
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
                    sender.sendMessage((new StringBuilder()).append(ChatColor.GREEN).append("你还可以创建").append(wtpPlayers.getPlayerSpareString((Player) sender)).append("个地标").toString());
                sender.sendMessage(
                        (new StringBuilder(WTP.prefix)).append(ChatColor.AQUA).append("== 命令菜单 ==").toString());
                sender.sendMessage(String.valueOf(ChatColor.GOLD) + "/w <地标名字>" + ChatColor.GRAY +
                        " - " + ChatColor.LIGHT_PURPLE + "传送到公共地标");
                sender.sendMessage(
                        String.valueOf(ChatColor.GOLD) + "/wlist [页数]" + ChatColor.GRAY +
                                " - " + ChatColor.LIGHT_PURPLE + "查看公共地标列表");

                sender.sendMessage((new StringBuilder()).append(ChatColor.GOLD).append("/wtp create <地标名字>")
                        .append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE).append("花费").append(WTPConfig.createFee).append("创建一个公共地标").toString());
                sender.sendMessage(String.valueOf(ChatColor.GOLD) + "/wtp alias <地标名字> <地标别名>" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "花费" + WTPConfig.aliasFee + "给公共地标添加别名");
                sender.sendMessage(String.valueOf(ChatColor.GOLD) + "/wtp msg <地标名字> <欢迎信息>" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "花费" + WTPConfig.welcomeFee + "给公共地标添加欢迎信息");
                sender.sendMessage(String.valueOf(ChatColor.GOLD) + "/wtp relocate <地标名字>" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "花费" + WTPConfig.relocationFee + "更改公共地标为当前位置");
                sender.sendMessage(
                        String.valueOf(ChatColor.GOLD) + "/wtp about" + ChatColor.GRAY +
                                " - " + ChatColor.LIGHT_PURPLE + "查看插件信息");

                if (!(sender instanceof Player)) {
                    sender.sendMessage(
                            (new StringBuilder(WTP.prefix)).append(ChatColor.RED).append("只有玩家能执行这些命令！").toString());
                }
                return true;
            }
        }
        return false;
    }
}
