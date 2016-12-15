/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.files;

import cc.isotopestudio.WTP.WTP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class WTPPlayers {
    private final WTP plugin;
    private final WTPData wtpData;

    public WTPPlayers(WTP plugin) {
        this.plugin = plugin;
        wtpData = new WTPData(plugin);
    }

    public void tpWarp(Player player, String name) {
        if (plugin.getWarpsData().isSet(name)) {
            World world = Bukkit.getServer().getWorld(plugin.getWarpsData().getString(name + ".world"));
            Location loc = new Location(world, plugin.getWarpsData().getDouble(name + ".X"),
                    plugin.getWarpsData().getDouble(name + ".Y"), plugin.getWarpsData().getDouble(name + ".Z"),
                    (float) plugin.getWarpsData().getDouble(name + ".yaw"),
                    (float) plugin.getWarpsData().getDouble(name + ".pitch"));
            player.teleport(loc);
            StringBuilder msg = new StringBuilder(WTP.prefix);
            String alias = wtpData.getAlias(name);
            String welcome = wtpData.getMsg(name);
            if (alias != null)
                msg.append(ChatColor.GOLD).append(alias).append("  ");
            if (welcome != null)
                msg.append(ChatColor.AQUA).append(welcome);
            if (alias == null && welcome == null) {
                player.sendMessage(msg.append(ChatColor.YELLOW).append("���͵�").append(name).toString());
            } else
                player.sendMessage(msg.toString());
        } else {
            player.sendMessage(WTP.prefix + ChatColor.RED + "�ر겻����");

        }
    }

    public int getPlayerWarpNum(Player player) {
        List<String> warpsList = plugin.getPlayersData().getStringList(player.getName() + ".warps");
        return warpsList.size();
    }

    public int getPlayerWarpLim(Player player) {
        int limit;
        if (player.isOp() || player.hasPermission("WTP.admin")) {
            return WTPConfig.getLimit("admin");
        } else {
            Set<String> groupList = WTPConfig.limitation.keySet();
            for (String temp : groupList) {
                if (player.hasPermission("WTP.create." + temp)) {
                    limit = WTPConfig.getLimit(temp);
                    return limit;
                }
            }
        }
        return WTPConfig.getLimit("default");
    }

    public int getPlayerSpare(Player player) {
        int spare = getPlayerWarpLim(player) - getPlayerWarpNum(player);
        if (getPlayerWarpLim(player) == -1) {
            return -1;
        }
        if (spare < 0) {
            return 0;
        }
        return getPlayerWarpLim(player) - getPlayerWarpNum(player);
    }

    public String getPlayerSpareString(Player player) {
        if (getPlayerWarpLim(player) != -1) {
            int limit = getPlayerSpare(player);
            return limit + "";
        } else
            return "����";
    }

    public boolean isOwner(Player player, String name) {
        String owner = plugin.getWarpsData().getString(name + ".owner");
        return owner != null && owner.equals(player.getName());
    }

    public List<String> getPlayerWarpsList(String player) {
        return plugin.getPlayersData().getStringList(player + ".warps");
    }

}
