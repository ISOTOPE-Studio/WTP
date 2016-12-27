/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.commands;

import cc.isotopestudio.WTP.gui.WarpGUI;
import cc.isotopestudio.WTP.util.S;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWlist implements CommandExecutor {
    public String[][] warpListMsg;
    public int pages = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("wlist")) {
            if (pages > 0) {
                if (args.length == 0) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        new WarpGUI(player, 0).open(player);
                    }
                    sendWlistMsg(sender, 0);
                    return true;
                }
                int page = CommandsUti.getNumber(args[0], -1);
                if (page > pages || page < 1) {
                    sender.sendMessage(S.toPrefixRed("一共只有" + pages + "页"));
                    sendWlistMsg(sender, 0);
                    return true;
                }
                sendWlistMsg(sender, page - 1);
                return true;
            } else {
                sender.sendMessage(S.toPrefixRed("服务器没有公共地标"));
                return true;
            }
        }
        return false;
    }

    private void sendWlistMsg(CommandSender sender, int page) {
        for (int line = 0; line < 9; line++) {
            String msg = warpListMsg[page][line];
            if (msg != null)
                sender.sendMessage(msg);
        }
    }
}
