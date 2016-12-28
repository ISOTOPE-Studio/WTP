/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.data;

import cc.isotopestudio.WTP.tasks.UpdateWlist;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        setItem(name, new ItemStack(Material.WOOL, 1, (short) (Math.random() * 16)));
        List<String> warpList = playerData.getStringList(player.getName() + ".warps");
        warpList.add(name);
        playerData.set(player.getName() + ".warps", warpList);
        playerData.save();
        warpData.save();
        UpdateWlist.updateWlist(plugin);
    }

    public static void deleteWarp(String name) {
        List<String> warpsList = playerData.getStringList(getOwner(name) + ".warps");
        warpsList.remove(name);
        playerData.set(getOwner(name) + ".warps", warpsList);

        List<String> favoritePlayerList = warpData.getStringList(name + ".favorite");
        for (String player : favoritePlayerList) {
            List<String> playerFavorites = playerData.getStringList(player + ".favorite");
            if (playerFavorites.remove(name)) {
                playerData.set(player + ".favorite", playerFavorites);
            }
        }

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

    public static void setAlias(String name, String alias) {
        warpData.set(name + ".alias", alias);
        warpData.save();
    }

    public static void setMsg(String name, String msg) {
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

    public static void setItem(String name, ItemStack item) {
        warpData.set(name + ".item", item.clone());
        warpData.save();
    }

    public static ItemStack getItem(String name) {
        if (!warpData.isSet(name + ".item")) {
            warpData.set(name + ".item", new ItemStack(Material.WOOL, 1, (short) (Math.random() * 16)));
            warpData.save();
        }
        return warpData.getItemStack(name + ".item");
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
