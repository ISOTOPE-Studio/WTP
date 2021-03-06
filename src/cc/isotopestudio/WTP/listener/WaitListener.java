/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.listener;

import cc.isotopestudio.WTP.WTP;
import cc.isotopestudio.WTP.data.WTPConfig;
import cc.isotopestudio.WTP.data.WTPData;
import cc.isotopestudio.WTP.data.WTPPlayers;
import cc.isotopestudio.WTP.util.S;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WaitListener implements Listener {

    public static Map<Player, String> renameWait = new HashMap<>();
    public static Map<Player, String> msgWait = new HashMap<>();
    public static Map<Player, String> itemWait = new HashMap<>();
    public static Set<Player> createWait = new HashSet<>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        String msg = ChatColor.stripColor(event.getMessage());
        if (renameWait.containsKey(player)) {
            event.setCancelled(true);
            String warpName = renameWait.remove(player);
            if (msg.contains("cancel")) {
                player.sendMessage(S.toPrefixRed("取消操作"));
                return;
            }
            if (msg.length() >= 15) {
                player.sendMessage(S.toPrefixRed("别名不能超过15个字 请再试一次"));
                renameWait.put(player, warpName);
                return;
            }
            if (!WTP.econ.withdrawPlayer(player, WTPConfig.aliasFee).transactionSuccess()) {
                player.sendMessage(S.toPrefixRed("你的金钱不足"));
                return;
            }
            WTPData.setAlias(warpName, ChatColor.stripColor(msg));
            player.sendMessage(S.toPrefixGreen("成功添加别名！"));
        } else if (msgWait.containsKey(player)) {
            event.setCancelled(true);
            String warpName = msgWait.remove(player);
            if (msg.contains("cancel")) {
                player.sendMessage(S.toPrefixRed("取消操作"));
                return;
            }
            if (msg.length() >= 30) {
                player.sendMessage(
                        S.toPrefixRed("欢迎信息不能超过30个字 请再试一次"));
                msgWait.put(player, warpName);
                return;
            }
            if (!WTP.econ.withdrawPlayer(player, WTPConfig.welcomeFee).transactionSuccess()) {
                player.sendMessage(S.toPrefixRed("你的金钱不足"));
                return;
            }
            WTPData.setMsg(warpName, msg);
            player.sendMessage(S.toPrefixGreen("成功添加欢迎信息！"));
        } else if (createWait.remove(player)) {
            event.setCancelled(true);
            if (msg.contains("cancel")) {
                player.sendMessage(S.toPrefixRed("取消操作"));
                return;
            }
            if (WTPPlayers.getPlayerSpare(player) <= 0 && WTPPlayers.getPlayerWarpLim(player) != -1) {
                player.sendMessage(S.toPrefixRed("你不能再创建更多的地标了"));
                return;
            }
            if (!msg.matches("^[a-zA-Z1-9]*")) {
                player.sendMessage(S.toPrefixRed("名字只能为英文字母 请再试一次"));
                createWait.add(player);
                return;
            }
            if (msg.length() >= 10) {
                player.sendMessage(S.toPrefixRed("名字不能超过十个字母 请再试一次"));
                createWait.add(player);
                return;
            }
            if (WTPData.ifWarpExist(msg)) {
                player.sendMessage(S.toPrefixRed("地标" + msg + "已经存在，换个名字吧 请再试一次"));
                createWait.add(player);
                return;
            }
            if (!WTP.econ.withdrawPlayer(player, WTPConfig.createFee).transactionSuccess()) {
                player.sendMessage(S.toPrefixRed("你的金钱不足"));
                return;
            }
            WTPData.addWarp(player, msg.toLowerCase());
            player.sendMessage(S.toPrefixGreen("成功创建！"));
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final Player player = event.getPlayer();
            if (itemWait.containsKey(player)) {
                event.setCancelled(true);
                String warpName = itemWait.remove(player);
                ItemStack item = event.getItem();
                if (item == null || item.getType() == Material.AIR) {
                    player.sendMessage(S.toPrefixRed("取消操作"));
                    return;
                }
                if (WTP.econ.getBalance(player) < WTPConfig.itemFee) {
                    player.sendMessage(S.toPrefixRed("你的金钱不足"));
                    return;
                }
                WTP.econ.withdrawPlayer(player, WTPConfig.itemFee);
                WTPData.setItem(warpName, item);
                player.sendMessage(S.toPrefixGreen("成功添加物品！"));
            }
        }
    }
}
