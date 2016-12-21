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

import static cc.isotopestudio.WTP.WTP.*;

public class WTPData {

    public static void addWarp(Player player, String name) {
        warpData.set(name + ".owner", player.getName());
        Location loc = player.getLocation();
        warpData.set(name + ".world", loc.getWorld().getName());
        warpData.set(name + ".X", loc.getX());
        warpData.set(name + ".Y", loc.getY());
        warpData.set(name + ".Z", loc.getZ());
        warpData.set(name + ".pitch", loc.getPitch());
        warpData.set(name + ".yaw", loc.getYaw());
        List<String> warpList = playerData.getStringList(player.getName() + ".warps");
        warpList.add(name);
        playerData.set(player.getName() + ".warps", warpList);
        playerData.save();
        warpData.save();
        UpdateWlist.updateWlist(plugin);
    }

    public static void deleteWarp(String name) {
        List<String> warpsList = playerData.getStringList(getOwner(name) + ".warps");
        int index = 0;
        for (String temp : warpsList) {
            if (temp.equals(name)) {
                warpsList.remove(index);
                break;
            }
            index++;
        }
        playerData.set(getOwner(name) + ".warps", warpsList);

        warpData.set(name, null);

        playerData.save();
        warpData.save();
        UpdateWlist.updateWlist(plugin);
    }

    public static boolean ifWarpExist(String name) {
        Set<String> list = warpData.getKeys(false);
        for (String temp : list) {
            if (temp.equals(name))
                return true;
        }
        return false;
    }

    public static void addAlias(String name, String alias) {
        warpData.set(name + ".alias", alias);
        warpData.save();
    }

    public static void addMsg(String name, String msg) {
        warpData.set(name + ".welcome", msg);
        warpData.save();
    }

    public static void relocate(String name, Player player) {
        Location loc = player.getLocation();
        warpData.set(name + ".world", loc.getWorld().getName());
        warpData.set(name + ".X", loc.getX());
        warpData.set(name + ".Y", loc.getY());
        warpData.set(name + ".Z", loc.getZ());
        warpData.set(name + ".pitch", loc.getPitch());
        warpData.set(name + ".yaw", loc.getYaw());
        warpData.save();
    }

    public static String getOwner(String name) {
        return warpData.getString(name + ".owner");
    }

    public static String getAlias(String name) {
        if (warpData.isSet(name + ".alias"))
            return warpData.getString(name + ".alias");
        return null;
    }

    public static String getMsg(String name) {
        if (warpData.isSet(name + ".welcome"))
            return warpData.getString(name + ".welcome");
        return null;
    }

    public static String getWarpDes(String name) {
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
