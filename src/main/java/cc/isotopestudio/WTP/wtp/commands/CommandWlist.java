package cc.isotopestudio.WTP.wtp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cc.isotopestudio.WTP.wtp.WTP;

public class CommandWlist  implements CommandExecutor {
	private final WTP plugin;

	public CommandWlist(WTP plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("wlist")) {
			
			return true;
		}
		return false;
	}
}
