/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.gui;

import cc.isotopestudio.WTP.WTP;
import cc.isotopestudio.WTP.files.WTPConfig;
import cc.isotopestudio.WTP.files.WTPData;
import cc.isotopestudio.WTP.util.S;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static cc.isotopestudio.WTP.listener.ChatListener.msgWait;
import static cc.isotopestudio.WTP.listener.ChatListener.renameWait;

public class ManagerGUI extends GUI {

    private final String warp;

    ManagerGUI(Player player, String warp) {
        super(S.toBoldGold("�ر����") + "[" + warp+ "]", 9, player);
        this.page = 0;
        this.warp = warp;
        setOption(1, new ItemStack(Material.NAME_TAG), S.toBoldGold("������"), S.toYellow("����" + WTPConfig.aliasFee + "�������ر���ӱ���"));
        setOption(2, new ItemStack(Material.PAPER), S.toBoldGold("���û�ӭ��Ϣ"), S.toYellow("����" + WTPConfig.welcomeFee + "�������ر���ӻ�ӭ��Ϣ"));
        setOption(3, new ItemStack(Material.COMPASS), S.toBoldGold("����λ��"), S.toYellow("����" + WTPConfig.relocationFee + "���Ĺ����ر�Ϊ��ǰλ��"));

    }

    private void onRename() {
        if (WTP.econ.getBalance(player.getName()) < WTPConfig.aliasFee) {
            player.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
            return;
        }
        renameWait.put(player, warp);
        player.sendMessage(S.toPrefixYellow("������� ���� cancel ȡ��"));
    }

    private void onMsg() {
        if (WTP.econ.getBalance(player.getName()) < WTPConfig.welcomeFee) {
            player.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
            return;
        }
        msgWait.put(player, warp);
        player.sendMessage(S.toPrefixYellow("���뻶ӭ��Ϣ ���� cancel ȡ��"));
    }

    private void onRelocate() {
        if (WTP.econ.getBalance(player.getName()) < WTPConfig.relocationFee) {
            player.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
            return;
        }
        WTP.econ.withdrawPlayer(player.getName(), WTPConfig.relocationFee);
        WTPData.relocate(warp, player);
        player.sendMessage(S.toPrefixGreen("�ɹ��ı䴫�͵㣡"));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClick(final InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals(name) && playerName.equals(event.getWhoClicked().getName())) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            if (slot < 0 || slot >= size) {
                return;
            }

            if (optionIcons[slot] != null) {
                switch (slot) {
                    case (1):
                        onRename();
                        break;
                    case (2):
                        onMsg();
                        break;
                    case (3):
                        onRelocate();
                        break;
                }
                player.closeInventory();
            }
        }
    }

}
