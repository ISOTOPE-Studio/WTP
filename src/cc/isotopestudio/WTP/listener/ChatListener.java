/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.listener;

import cc.isotopestudio.WTP.WTP;
import cc.isotopestudio.WTP.files.WTPConfig;
import cc.isotopestudio.WTP.files.WTPData;
import cc.isotopestudio.WTP.util.S;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;

public class ChatListener implements Listener {

    public static Map<Player, String> renameWait = new HashMap<>();
    public static Map<Player, String> msgWait = new HashMap<>();

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
            WTPData.addAlias(renameWait.remove(player), ChatColor.stripColor(msg));
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
            WTPData.addMsg(msgWait.remove(player), msg);
            player.sendMessage(S.toPrefixGreen("�ɹ���ӻ�ӭ��Ϣ��"));
        }
    }
}
