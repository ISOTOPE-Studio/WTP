package cc.isotopestudio.WTP.wtp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cc.isotopestudio.WTP.wtp.WTP;
import cc.isotopestudio.WTP.wtp.files.WTPConfig;

public class CommandWtpadmin implements CommandExecutor {
	private final WTP plugin;

	public CommandWtpadmin(WTP plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("wtpadmin")) {
			if (!sender.hasPermission("WTP.admin")) {
				sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("你没有权限").toString());
				return true;
			}
			if (args.length > 0 && !args[0].equals("help")) {
				if (args[0].equalsIgnoreCase("player")) {
					if (args.length == 2) {

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
				sender.sendMessage(
						new StringBuilder().append(ChatColor.GOLD).append("/wtpadmin about").append(ChatColor.GRAY)
								.append(" - ").append(ChatColor.LIGHT_PURPLE).append("查看插件信息").toString());

				return true;
			}
		}
		return false;
	}
}
