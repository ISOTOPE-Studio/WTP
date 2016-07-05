package cc.isotopestudio.WTP.wtp.commands;

import cc.isotopestudio.WTP.wtp.WTP;
import cc.isotopestudio.WTP.wtp.files.WTPConfig;
import cc.isotopestudio.WTP.wtp.files.WTPData;
import cc.isotopestudio.WTP.wtp.files.WTPPlayers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandWtpadmin implements CommandExecutor {
    private final WTP plugin;
    private final WTPData wtpData;
    private final WTPPlayers wtpPlayers;

    public CommandWtpadmin(WTP plugin) {
        this.plugin = plugin;
        wtpData = new WTPData(plugin);
        wtpPlayers = new WTPPlayers(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("wtpadmin")) {
            if (!sender.hasPermission("WTP.admin")) {
                sender.sendMessage(String.valueOf(ChatColor.RED) + "你没有权限");
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
                                    .append(ChatColor.ITALIC).append("还可以拥有").append(wtpPlayers.getPlayerSpareString(player)).append("个，已拥有").append(wtpPlayers.getPlayerWarpNum(player)).append("个公共地标")
                                    .toString());

                        List<String> warpsList = wtpPlayers.getPlayerWarpsList(playerName);
                        for (String aWarpsList : warpsList) {
                            sender.sendMessage(wtpData.getWarpDes(aWarpsList));
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
                        if (!wtpData.ifWarpExist(args[1])) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "地标不存在");
                            return true;
                        }
                        sender.sendMessage((new StringBuilder(WTP.prefix)).append(ChatColor.AQUA).append("地标: ")
                                .append(args[1]).toString());
                        sender.sendMessage(wtpData.getWarpDes(args[1]));
                        sender.sendMessage((new StringBuilder("    ")).append(ChatColor.AQUA).append("拥有着: ")
                                .append(wtpData.getOwner(args[1])).toString());
                        return true;
                    } else {
                        sender.sendMessage(String.valueOf(ChatColor.RED) + "/wtpadmin warp <地标名字>" +
                                ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "查看公共地标信息");

                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("delete")) {
                    if (args.length == 2) {
                        if (!wtpData.ifWarpExist(args[1])) {
                            sender.sendMessage(String.valueOf(ChatColor.RED) + "地标不存在");
                            return true;
                        }
                        wtpData.deleteWarp(args[1]);
                        sender.sendMessage(
                                String.valueOf(ChatColor.AQUA) + "成功删除" + args[1]);
                        return true;
                    } else {
                        sender.sendMessage(String.valueOf(ChatColor.RED) + "/wtpadmin delete <地标名字>" +
                                ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "删除一个公共地标");
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
                            String.valueOf(ChatColor.RED) + "未知命令，输入/wtpadmin查看帮助");
                    return true;
                }
            } else { // Help Menu
                sender.sendMessage(
                        (new StringBuilder(WTP.prefix)).append(ChatColor.AQUA).append("== 管理员菜单 ==").toString());
                sender.sendMessage((new StringBuilder()).append(ChatColor.GOLD).append("/wtpadmin player <玩家名字>")
                        .append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE).append("查看玩家的地标信息")
                        .toString());
                sender.sendMessage(String.valueOf(ChatColor.GOLD) + "/wtpadmin warp <地标名字>" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "查看公共地标信息");
                sender.sendMessage(String.valueOf(ChatColor.GOLD) + "/wtpadmin delete <地标名字>" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "删除一个公共地标");
                sender.sendMessage(String.valueOf(ChatColor.GOLD) + "/wtpadmin reload" +
                        ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + "重载插件");
                sender.sendMessage(
                        String.valueOf(ChatColor.GOLD) + "/wtpadmin about" + ChatColor.GRAY +
                                " - " + ChatColor.LIGHT_PURPLE + "查看插件信息");

                return true;
            }
        }
        return false;
    }
}
