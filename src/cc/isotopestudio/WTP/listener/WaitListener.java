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
            if (msg.contains("cancel")) {
                player.sendMessage(S.toPrefixRed("ȡ������"));
                return;
            }
            if (msg.length() >= 15) {
                player.sendMessage(S.toPrefixRed("�������ܳ���15����"));
                return;
            }
            if (WTP.econ.getBalance(player.getName()) < WTPConfig.aliasFee) {
                player.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
                return;
            }
            WTP.econ.withdrawPlayer(player.getName(), WTPConfig.aliasFee);
            WTPData.setAlias(renameWait.remove(player), ChatColor.stripColor(msg));
            player.sendMessage(S.toPrefixGreen("�ɹ���ӱ�����"));
        } else if (msgWait.containsKey(player)) {
            event.setCancelled(true);
            if (msg.contains("cancel")) {
                player.sendMessage(S.toPrefixRed("ȡ������"));
                return;
            }
            if (msg.length() >= 30) {
                player.sendMessage(
                        S.toPrefixRed("��ӭ��Ϣ���ܳ���30����"));
                return;
            }
            if (WTP.econ.getBalance(player.getName()) < WTPConfig.welcomeFee) {
                player.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
                return;
            }
            WTP.econ.withdrawPlayer(player.getName(), WTPConfig.welcomeFee);
            WTPData.setMsg(msgWait.remove(player), msg);
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
            if (!msg.matches("^[a-zA-Z]*")) {
                player.sendMessage(S.toPrefixRed("����ֻ��ΪӢ����ĸ"));
                return;
            }
            if (msg.length() >= 10) {
                player.sendMessage(S.toPrefixRed("���ֲ��ܳ���ʮ����ĸ"));
                return;
            }
            if (WTPData.ifWarpExist(msg)) {
                player.sendMessage(S.toPrefixRed("�ر�" + msg + "�Ѿ����ڣ��������ְ�"));
                return;
            }
            if (WTP.econ.getBalance(player.getName()) < WTPConfig.createFee) {
                player.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
                return;
            }
            WTP.econ.withdrawPlayer(player.getName(), WTPConfig.createFee);
            WTPData.addWarp(player, msg);
            player.sendMessage(S.toPrefixGreen("�ɹ�������"));
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final Player player = event.getPlayer();
            if (itemWait.containsKey(player)) {
                event.setCancelled(true);
                ItemStack item = event.getItem();
                if (item == null || item.getType() == Material.AIR) {
                    player.sendMessage(S.toPrefixRed("ȡ������"));
                    return;
                }
                if (WTP.econ.getBalance(player.getName()) < WTPConfig.itemFee) {
                    player.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
                    return;
                }
                WTP.econ.withdrawPlayer(player.getName(), WTPConfig.itemFee);
                WTPData.setItem(itemWait.remove(player), item);
                player.sendMessage(S.toPrefixGreen("�ɹ������Ʒ��"));
            }
        }
    }
}
