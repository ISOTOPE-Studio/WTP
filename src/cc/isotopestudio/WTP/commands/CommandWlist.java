/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandWlist implements CommandExecutor {
    public String[][] warpListMsg;
    public int pages = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("wlist")) {
            if (pages > 0) {
                if (args.length == 0) {
                    sendWlistMsg(sender, 0);
                    return true;
                }
                int page = CommandsUti.getNumber(args[0], -1);
                if (page > pages || page < 1) {
                    sender.sendMessage(
                            String.valueOf(ChatColor.RED) + "一共只有" + pages + "页");
                    sendWlistMsg(sender, 0);
                    return true;
                }
                sendWlistMsg(sender, page - 1);
                return true;
            } else {
                sender.sendMessage(String.valueOf(ChatColor.RED) + "服务器没有公共地标");
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
