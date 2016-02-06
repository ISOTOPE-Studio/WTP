package cc.isotopestudio.WTP.wtp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cc.isotopestudio.WTP.wtp.WTP;
import cc.isotopestudio.WTP.wtp.files.WTPConfig;
import cc.isotopestudio.WTP.wtp.files.WTPData;
import cc.isotopestudio.WTP.wtp.files.WTPPlayers;

public class CommandWtp implements CommandExecutor {
	private final WTP plugin;
	private final WTPData wtpData;
	private final WTPPlayers wtpPlayers;

	public CommandWtp(WTP plugin) {
		this.plugin = plugin;
		wtpData = new WTPData(plugin);
		wtpPlayers = new WTPPlayers(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 0 && !args[0].equals("help") && sender instanceof Player) {
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("wtp")) {
				if (args[0].equals("create")) {
					if (args.length == 2) {
						if (wtpPlayers.getPlayerSpare(player) <= 0
								&& wtpPlayers.getPlayerWarpLim(player) != -1) {
							sender.sendMessage(
									new StringBuilder().append(ChatColor.RED).append("你不能再创建更多的地标了").toString());
							return true;
						}
						if (!args[1].matches("^[a-zA-Z]*")) {
							sender.sendMessage(
									new StringBuilder().append(ChatColor.RED).append("名字只能为英文字母").toString());
							return true;
						}
						if (args[1].length() >= 10) {
							sender.sendMessage(
									new StringBuilder().append(ChatColor.RED).append("名字不能超过十个字母").toString());
							return true;
						}
						
						if (WTP.econ.getBalance(player.getName()) < WTPConfig.createFee) {
							sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("你的金钱不足").toString());
							return true;
						}
						WTP.econ.withdrawPlayer(player.getName(), WTPConfig.createFee);
						sender.sendMessage(new StringBuilder().append(ChatColor.AQUA).append("成功创建！").toString());
						
						wtpData.addWarp(player, args[1]);
						
						return true;
					} else {
						sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("/wtp create <地标名字>")
								.append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE)
								.append("花费" + WTPConfig.createFee + "创建一个公共地标(名字强烈建议不要输入中文)").toString());
						return true;
					}
				}
				if (args[0].equals("name")) {
					if (args.length == 3) {
						return true;
					} else {
						sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("/wtp name <地标名字> <地标别名>")
								.append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE)
								.append("花费" + WTPConfig.aliasFee + "给公共地标添加别名").toString());
						return true;
					}
				}
				if (args[0].equals("msg")) {
					if (args.length == 3) {
						return true;
					} else {
						sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("/wtp msg <地标名字> <欢迎信息>")
								.append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE)
								.append("花费" + WTPConfig.welcomeFee + "给公共地标添加欢迎信息").toString());
						return true;
					}
				}
				if (args[0].equals("about")) {
					about(sender);
					return true;
				}
			}
		} else { // Help Menu
			sender.sendMessage((new StringBuilder(plugin.prefix)).append(ChatColor.AQUA).append("== 帮助 ==").toString());
			sender.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("这是一个可以创建公共地标的插件，其他玩家可以输入你的")
					.append(ChatColor.RED).append(ChatColor.ITALIC).append("地标名字").append(ChatColor.RESET)
					.append(ChatColor.YELLOW).append("传送过来").toString());
			sender.sendMessage((new StringBuilder()).append(ChatColor.YELLOW).append("传送时玩家将会看到你设置的地标")
					.append(ChatColor.RED).append(ChatColor.ITALIC).append("欢迎信息").toString());
			sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append(ChatColor.ITALIC).append("地标别名")
					.append(ChatColor.RESET).append(ChatColor.YELLOW).append("是其他玩家看到地标的简短中文介绍").toString());
			if (sender instanceof Player)
				sender.sendMessage((new StringBuilder()).append(ChatColor.GREEN)
						.append("你还可以创建" + wtpPlayers.getPlayerSpareString((Player) sender) + "个地标")
						.toString());
			sender.sendMessage(
					(new StringBuilder(plugin.prefix)).append(ChatColor.AQUA).append("== 命令菜单 ==").toString());
			sender.sendMessage((new StringBuilder()).append(ChatColor.GOLD).append("/wtp create <地标名字>")
					.append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE)
					.append("花费" + WTPConfig.createFee + "创建一个公共地标").toString());
			sender.sendMessage(new StringBuilder().append(ChatColor.GOLD).append("/wtp name <地标名字> <地标别名>")
					.append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE)
					.append("花费" + WTPConfig.aliasFee + "给公共地标添加别名").toString());
			sender.sendMessage(new StringBuilder().append(ChatColor.GOLD).append("/wtp msg <地标名字> <欢迎信息>")
					.append(ChatColor.GRAY).append(" - ").append(ChatColor.LIGHT_PURPLE)
					.append("花费" + WTPConfig.welcomeFee + "给公共地标添加欢迎信息").toString());
			sender.sendMessage(new StringBuilder().append(ChatColor.GOLD).append("/wtp about").append(ChatColor.GRAY)
					.append(" - ").append(ChatColor.LIGHT_PURPLE).append("查看插件信息").toString());

			if (!(sender instanceof Player)) {
				sender.sendMessage(
						(new StringBuilder(plugin.prefix)).append(ChatColor.RED).append("只有玩家能执行这些命令！").toString());
			}
			return true;
		}
		return false;
	}

	public void about(CommandSender sender) {
		sender.sendMessage((new StringBuilder()).append(ChatColor.GRAY).append("---- " + plugin.prefix)
				.append(ChatColor.RESET).append(ChatColor.DARK_GRAY).append(" " + plugin.version).append(ChatColor.GRAY)
				.append(" ----").toString());
		sender.sendMessage((new StringBuilder()).append(ChatColor.BLUE).append(ChatColor.ITALIC).append("为服务器制作的公共地标插件")
				.toString());
		sender.sendMessage((new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("制作： ")
				.append(ChatColor.RESET).append(ChatColor.AQUA).append("Mars (ISOTOPE Studio)").toString());
		sender.sendMessage((new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("网址： ")
				.append(ChatColor.RESET).append(ChatColor.AQUA).append("http://isotopestudio.cc/minecraft.html")
				.toString());
	}
}
