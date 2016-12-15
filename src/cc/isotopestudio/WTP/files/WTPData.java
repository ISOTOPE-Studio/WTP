/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.files;

import cc.isotopestudio.WTP.WTP;
import cc.isotopestudio.WTP.tasks.UpdateWlist;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class WTPData {

    private final WTP plugin;

    public WTPData(WTP plugin) {
        this.plugin = plugin;
    }

    public void addWarp(Player player, String name) {
        plugin.getWarpsData().set(name + ".owner", player.getName());
        Location loc = player.getLocation();
        plugin.getWarpsData().set(name + ".world", loc.getWorld().getName());
        plugin.getWarpsData().set(name + ".X", loc.getX());
        plugin.getWarpsData().set(name + ".Y", loc.getY());
        plugin.getWarpsData().set(name + ".Z", loc.getZ());
        plugin.getWarpsData().set(name + ".pitch", loc.getPitch());
        plugin.getWarpsData().set(name + ".yaw", loc.getYaw());
        List<String> warpList = plugin.getPlayersData().getStringList(player.getName() + ".warps");
        warpList.add(name);
        plugin.getPlayersData().set(player.getName() + ".warps", warpList);
        plugin.savePlayersData();
        plugin.saveWarpsData();
        UpdateWlist.updateWlist(plugin);
    }

    public void deleteWarp(String name) {
        List<String> warpsList = plugin.getPlayersData().getStringList(getOwner(name) + ".warps");
        int index = 0;
        for (String temp : warpsList) {
            if (temp.equals(name)) {
                warpsList.remove(index);
                break;
            }
            index++;
        }
        plugin.getPlayersData().set(getOwner(name) + ".warps", warpsList);

        plugin.getWarpsData().set(name, null);

        plugin.savePlayersData();
        plugin.saveWarpsData();
        UpdateWlist.updateWlist(plugin);
    }

    public boolean ifWarpExist(String name) {
        Set<String> list = plugin.getWarpsData().getKeys(false);
        for (String temp : list) {
            if (temp.equals(name))
                return true;
        }
        return false;
    }

    public void addAlias(String name, String alias) {
        plugin.getWarpsData().set(name + ".alias", alias);
        plugin.saveWarpsData();
    }

    public void addMsg(String name, String msg) {
        plugin.getWarpsData().set(name + ".welcome", msg);
        plugin.saveWarpsData();
    }

    public void relocate(String name, Player player) {
        Location loc = player.getLocation();
        plugin.getWarpsData().set(name + ".world", loc.getWorld().getName());
        plugin.getWarpsData().set(name + ".X", loc.getX());
        plugin.getWarpsData().set(name + ".Y", loc.getY());
        plugin.getWarpsData().set(name + ".Z", loc.getZ());
        plugin.getWarpsData().set(name + ".pitch", loc.getPitch());
        plugin.getWarpsData().set(name + ".yaw", loc.getYaw());
        plugin.saveWarpsData();
    }

    public String getOwner(String name) {
        return plugin.getWarpsData().getString(name + ".owner");
    }

    public String getAlias(String name) {
        if (plugin.getWarpsData().isSet(name + ".alias"))
            return plugin.getWarpsData().getString(name + ".alias");
        return null;
    }

    public String getMsg(String name) {
        if (plugin.getWarpsData().isSet(name + ".welcome"))
            return plugin.getWarpsData().getString(name + ".welcome");
        return null;
    }

    public static String getAlias(String name, WTP plugin) {
        if (plugin.getWarpsData().isSet(name + ".alias"))
            return plugin.getWarpsData().getString(name + ".alias");
        return null;
    }

    public static String getMsg(String name, WTP plugin) {
        if (plugin.getWarpsData().isSet(name + ".welcome"))
            return plugin.getWarpsData().getString(name + ".welcome");
        return null;
    }

    public String getWarpDes(String name) {
        StringBuilder msg = new StringBuilder("    ").append(ChatColor.GOLD);
        String alias = getAlias(name);
        if (alias != null) {
            msg.append(alias);
        }
        msg.append(ChatColor.GRAY).append("(").append(name).append(")");
        String welcome = getMsg(name);
        if (welcome != null) {
            msg.append(ChatColor.AQUA).append(" - ").append(welcome);
        }
        return msg.toString();
    }
}
