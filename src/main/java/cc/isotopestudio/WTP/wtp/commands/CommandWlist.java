package cc.isotopestudio.WTP.wtp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cc.isotopestudio.WTP.wtp.WTP;
import cc.isotopestudio.WTP.wtp.tasks.UpdateWlist;

public class CommandWlist implements CommandExecutor {
	private final WTP plugin;
	public String[][] warpListMsg ;
	public int pages = 0;

	public CommandWlist(WTP plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("wlist")) {
			UpdateWlist.updateWlist(plugin);
			if (pages > 0) {
				if (args.length == 0) {
					sendWlistMsg(sender, 0);
					return true;
				}
				int page = CommandsUti.getNumber(args[0], -1);
				if (page > pages || page < 1) {
					sender.sendMessage(
							new StringBuilder().append(ChatColor.RED).append("一共只有" + pages + "页").toString());
					sendWlistMsg(sender, 0);
					return true;
				}
				sendWlistMsg(sender, page - 1);
				sender.sendMessage(plugin.getWarpsData().getKeys(false).toString());
				return true;
			} else {
				sender.sendMessage(new StringBuilder().append(ChatColor.RED).append("服务器没有公共地标").toString());
				return true;
			}
		}
		return false;
	}

	public void sendWlistMsg(CommandSender sender, int page) {
		for (int line = 0; line < 9; line++) {
			String msg = warpListMsg[page][line];
			if (msg != null)
				sender.sendMessage(msg);
		}
	}
}
