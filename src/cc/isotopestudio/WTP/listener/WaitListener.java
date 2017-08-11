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
                player.sendMessage(S.toPrefixRed("ȡ������"));
                return;
            }
            if (msg.length() >= 15) {
                player.sendMessage(S.toPrefixRed("�������ܳ���15���� ������һ��"));
                renameWait.put(player, warpName);
                return;
            }
            if (!WTP.econ.withdrawPlayer(player, WTPConfig.aliasFee).transactionSuccess()) {
                player.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
                return;
            }
            WTPData.setAlias(warpName, ChatColor.stripColor(msg));
            player.sendMessage(S.toPrefixGreen("�ɹ���ӱ�����"));
        } else if (msgWait.containsKey(player)) {
            event.setCancelled(true);
            String warpName = msgWait.remove(player);
            if (msg.contains("cancel")) {
                player.sendMessage(S.toPrefixRed("ȡ������"));
                return;
            }
            if (msg.length() >= 30) {
                player.sendMessage(
                        S.toPrefixRed("��ӭ��Ϣ���ܳ���30���� ������һ��"));
                msgWait.put(player, warpName);
                return;
            }
            if (!WTP.econ.withdrawPlayer(player, WTPConfig.welcomeFee).transactionSuccess()) {
                player.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
                return;
            }
            WTPData.setMsg(warpName, msg);
            player.sendMessage(S.toPrefixGreen("�ɹ���ӻ�ӭ��Ϣ��"));
        } else if (createWait.remove(player)) {
            event.setCancelled(true);
            if (msg.contains("cancel")) {
                player.sendMessage(S.toPrefixRed("ȡ������"));
                return;
            }
            if (WTPPlayers.getPlayerSpare(player) <= 0 && WTPPlayers.getPlayerWarpLim(player) != -1) {
                player.sendMessage(S.toPrefixRed("�㲻���ٴ�������ĵر���"));
                return;
            }
            if (!msg.matches("^[a-zA-Z1-9]*")) {
                player.sendMessage(S.toPrefixRed("����ֻ��ΪӢ����ĸ ������һ��"));
                createWait.add(player);
                return;
            }
            if (msg.length() >= 10) {
                player.sendMessage(S.toPrefixRed("���ֲ��ܳ���ʮ����ĸ ������һ��"));
                createWait.add(player);
                return;
            }
            if (WTPData.ifWarpExist(msg)) {
                player.sendMessage(S.toPrefixRed("�ر�" + msg + "�Ѿ����ڣ��������ְ� ������һ��"));
                createWait.add(player);
                return;
            }
            if (!WTP.econ.withdrawPlayer(player, WTPConfig.createFee).transactionSuccess()) {
                player.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
                return;
            }
            WTPData.addWarp(player, msg.toLowerCase());
            player.sendMessage(S.toPrefixGreen("�ɹ�������"));
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
                    player.sendMessage(S.toPrefixRed("ȡ������"));
                    return;
                }
                if (WTP.econ.getBalance(player) < WTPConfig.itemFee) {
                    player.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
                    return;
                }
                WTP.econ.withdrawPlayer(player, WTPConfig.itemFee);
                WTPData.setItem(warpName, item);
                player.sendMessage(S.toPrefixGreen("�ɹ������Ʒ��"));
            }
        }
    }
}
