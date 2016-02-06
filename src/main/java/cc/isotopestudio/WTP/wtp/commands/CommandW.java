package cc.isotopestudio.WTP.wtp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cc.isotopestudio.WTP.wtp.WTP;
import cc.isotopestudio.WTP.wtp.files.WTPData;

public class CommandW implements CommandExecutor {
	private final WTP plugin;
	private final WTPData wtpData;

	public CommandW(WTP plugin) {
		this.plugin = plugin;
		wtpData = new WTPData(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("w")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				wtpData.tpWarp(player, args[0]);
				return true;
			}
		}
		return false;
	}
}
