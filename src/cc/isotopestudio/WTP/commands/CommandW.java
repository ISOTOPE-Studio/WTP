/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.commands;

import cc.isotopestudio.WTP.data.WTPPlayers;
import cc.isotopestudio.WTP.gui.FavoriteGUI;
import cc.isotopestudio.WTP.util.S;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static cc.isotopestudio.WTP.WTP.plugin;

public class CommandW implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("w")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args != null && args.length == 1) {
                    WTPPlayers.tpWarp(player, args[0]);
                    return true;
                } else {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            new FavoriteGUI(player).open(player);
                        }
                    }.runTaskLater(plugin,2);
                    return true;
//                    sender.sendMessage(S.toPrefixRed("/w <地标名字>") +
//                            ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE +
//                            "传送到公共地标，输入/wlist查看地标列表");
                }
            } else {
                sender.sendMessage(S.toPrefixRed("只有玩家能执行这个命令！"));
                return true;
            }
        }
        return false;
    }
}
