package cc.isotopestudio.WTP.wtp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cc.isotopestudio.WTP.wtp.WTP;
import cc.isotopestudio.WTP.wtp.files.WTPData;
import cc.isotopestudio.WTP.wtp.files.WTPPlayers;

public class CommandW implements CommandExecutor {
	private final WTP plugin;
	private final WTPPlayers wtpPlayers;

	public CommandW(WTP plugin) {
		this.plugin = plugin;
		wtpPlayers = new WTPPlayers(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("w")) {
			if (sender instanceof Player) {
				if (args != null && args.length == 1) {
					Player player = (Player) sender;
					wtpPlayers.tpWarp(player, args[0]);
					return true;
				}
			}
		}
		return false;
	}
}
