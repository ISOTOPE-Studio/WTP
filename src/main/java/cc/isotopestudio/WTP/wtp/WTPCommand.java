package cc.isotopestudio.WTP.wtp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WTPCommand implements CommandExecutor {
	private final WTP plugin;

	public WTPCommand(WTP plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("wtp")) {
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("w")) {
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("wlist")) {
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("wtpadmin")) {
			return true;
		}
		return false;
	}
}
