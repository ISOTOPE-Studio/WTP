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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static cc.isotopestudio.WTP.WTP.playerData;
import static cc.isotopestudio.WTP.WTP.warpData;

public class WTPPlayers {

    public static void tpWarp(Player player, String name) {
        if (warpData.isSet(name)) {
            World world = Bukkit.getServer().getWorld(warpData.getString(name + ".world"));
            Location loc = new Location(world, warpData.getDouble(name + ".X"),
                    warpData.getDouble(name + ".Y"), warpData.getDouble(name + ".Z"),
                    (float) warpData.getDouble(name + ".yaw"),
                    (float) warpData.getDouble(name + ".pitch"));
            player.teleport(loc);
            StringBuilder msg = new StringBuilder(WTP.prefix);
            String alias = WTPData.getAlias(name);
            String welcome = WTPData.getMsg(name);
            if (alias != null)
                msg.append(ChatColor.GOLD).append(alias).append("  ");
            if (welcome != null)
                msg.append(ChatColor.AQUA).append(welcome);
            if (alias == null && welcome == null) {
                player.sendMessage(msg.append(ChatColor.YELLOW).append("传送到").append(name).toString());
            } else
                player.sendMessage(msg.toString());
        } else {
            player.sendMessage(WTP.prefix + ChatColor.RED + "地标不存在");

        }
    }

    public static int getPlayerWarpNum(Player player) {
        List<String> warpsList = playerData.getStringList(player.getName() + ".warps");
        return warpsList.size();
    }

    public static int getPlayerWarpLim(Player player) {
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

    public static int getPlayerSpare(Player player) {
        int spare = getPlayerWarpLim(player) - getPlayerWarpNum(player);
        if (getPlayerWarpLim(player) == -1) {
            return -1;
        }
        if (spare < 0) {
            return 0;
        }
        return getPlayerWarpLim(player) - getPlayerWarpNum(player);
    }

    public static String getPlayerSpareString(Player player) {
        if (getPlayerWarpLim(player) != -1) {
            int limit = getPlayerSpare(player);
            return limit + "";
        } else
            return "无限";
    }

    public static boolean isOwner(Player player, String name) {
        String owner = warpData.getString(name + ".owner");
        return owner != null && owner.equals(player.getName());
    }

    public static List<String> getPlayerWarpsList(String player) {
        return playerData.getStringList(player + ".warps");
    }

    public static List<String> getPlayerFavoriteWarps(String player) {
        return playerData.getStringList(player + ".favorite");
    }

    public static void addPlayerFavoriteWarp(String player, String warpName) {
        List<String> list = new ArrayList<>(playerData.getStringList(player + ".favorite"));
        if (!list.contains(warpName)) {
            list.add(warpName);
            playerData.set(player + ".favorite", list);
            playerData.save();
        }
        list = new ArrayList<>(warpData.getStringList(warpName + ".favorite"));
        if (!list.contains(player)) {
            list.add(player);
            warpData.set(warpName + ".favorite", list);
            warpData.save();
        }
    }

    public static void removePlayerFavoriteWarp(String player, String warpName) {
        List<String> list = new ArrayList<>(playerData.getStringList(player + ".favorite"));
        list.remove(warpName);
        playerData.set(player + ".favorite", list);
        playerData.save();

        list = new ArrayList<>(warpData.getStringList(warpName + ".favorite"));
        list.remove(player);
        warpData.set(warpName + ".favorite", list);
        warpData.save();
    }

}
