package cc.isotopestudio.WTP.wtp.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cc.isotopestudio.WTP.wtp.WTP;
import cc.isotopestudio.WTP.wtp.files.WTPConfig;
import cc.isotopestudio.WTP.wtp.files.WTPData;
import cc.isotopestudio.WTP.wtp.files.WTPPlayers;

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
				sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("你没有权限").toString());
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
						sender.sendMessage((new StringBuilder(plugin.prefix)).append(ChatColor.AQUA).append("玩家")
								.append(args[1]).append("的公共地标").toString());
						if (player != null)
							sender.sendMessage((new StringBuilder("    ")).append(ChatColor.YELLOW)
									.append(ChatColor.ITALIC).append("还可以拥有" + wtpPlayers.getPlayerSpareString(player)
											+ "个，已拥有" + wtpPlayers.getPlayerWarpNum(player) + "个公共地标")
									.toString());

						List<String> warpsList = wtpPlayers.getPlayerWarpsList(playerName);
						for (int i = 0; i < warpsList.size(); i++) {
							sender.sendMessage(wtpData.getWarpDes(warpsList.get(i)));
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
							sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("地标不存在").toString());
							return true;
						}
						sender.sendMessage((new StringBuilder(plugin.prefix)).append(ChatColor.AQUA).append("地标: ")
								.append(args[1]).toString());
						sender.sendMessage(wtpData.getWarpDes(args[1]));
						sender.sendMessage((new StringBuilder("    ")).append(ChatColor.AQUA).append("拥有着: ")
								.append(wtpData.getOwner(args[1])).toString());
						return true;
					} else {
						sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("/wtpadmin warp <地标名字>")
								.append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE).append("查看公共地标信息")
								.toString());

						return true;
					}
				}
				if (args[0].equalsIgnoreCase("delete")) {
					if (args.length == 2) {
						if (!wtpData.ifWarpExist(args[1])) {
							sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("地标不存在").toString());
							return true;
						}
						wtpData.deleteWarp(args[1]);
						sender.sendMessage(
								new StringBuilder().append(ChatColor.AQUA).append("成功删除" + args[1]).toString());
						return true;
					} else {
						sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("/wtpadmin delete <地标名字>")
								.append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE).append("删除一个公共地标")
								.toString());
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
							new StringBuilder().append(ChatColor.RED).append("未知命令，输入/wtpadmin查看帮助").toString());
					return true;
				}
			} else { // Help Menu
				sender.sendMessage(
						(new StringBuilder(WTP.prefix)).append(ChatColor.AQUA).append("== 管理员菜单 ==").toString());
				sender.sendMessage((new StringBuilder()).append(ChatColor.GOLD).append("/wtpadmin player <玩家名字>")
						.append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE).append("查看玩家的地标信息")
						.toString());
				sender.sendMessage(new StringBuilder().append(ChatColor.GOLD).append("/wtpadmin warp <地标名字>")
						.append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE).append("查看公共地标信息")
						.toString());
				sender.sendMessage(new StringBuilder().append(ChatColor.GOLD).append("/wtpadmin delete <地标名字>")
						.append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE).append("删除一个公共地标")
						.toString());
				sender.sendMessage(new StringBuilder().append(ChatColor.GOLD).append("/wtpadmin reload")
						.append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE).append("重载插件").toString());
				sender.sendMessage(
						new StringBuilder().append(ChatColor.GOLD).append("/wtpadmin about").append(ChatColor.GRAY)
								.append(" - ").append(ChatColor.LIGHT_PURPLE).append("查看插件信息").toString());

				return true;
			}
		}
		return false;
	}
}
