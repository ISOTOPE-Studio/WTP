/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.gui;

import cc.isotopestudio.WTP.WTP;
import cc.isotopestudio.WTP.data.WTPConfig;
import cc.isotopestudio.WTP.data.WTPData;
import cc.isotopestudio.WTP.util.S;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static cc.isotopestudio.WTP.listener.WaitListener.itemWait;
import static cc.isotopestudio.WTP.listener.WaitListener.msgWait;
import static cc.isotopestudio.WTP.listener.WaitListener.renameWait;

public class ManageGUI extends GUI {

    private final String warp;

    ManageGUI(Player player, String warp) {
        super(S.toBoldGold("�ر����") + "[" + warp+ "]", 9, player);
        this.page = 0;
        this.warp = warp;
        setOption(0, new ItemStack(Material.BOOK_AND_QUILL), S.toBoldGold(warp), S.toYellow("����: " + WTPData.getAlias(warp)), S.toYellow("��ӭ��Ϣ: " + WTPData.getMsg(warp)));

        setOption(1, new ItemStack(Material.NAME_TAG), S.toBoldGold("������"), S.toYellow("����" + WTPConfig.aliasFee + "�������ر���ӱ���"));
        setOption(2, new ItemStack(Material.PAPER), S.toBoldGold("���û�ӭ��Ϣ"), S.toYellow("����" + WTPConfig.welcomeFee + "�������ر���ӻ�ӭ��Ϣ"));
        setOption(3, new ItemStack(Material.COMPASS), S.toBoldGold("����λ��"), S.toYellow("����" + WTPConfig.relocationFee + "���Ĺ����ر�Ϊ��ǰλ��"));
        setOption(4, new ItemStack(Material.DIAMOND_SWORD), S.toBoldGold("������Ʒ"), S.toYellow("����" + WTPConfig.itemFee + "����GUI����ʾ����Ʒ"));

    }

    private void onRename() {
        if (WTP.econ.getBalance(player) < WTPConfig.aliasFee) {
            player.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
            return;
        }
        renameWait.put(player, warp);
        player.sendMessage(S.toPrefixYellow("������� ���� cancel ȡ��"));
    }

    private void onMsg() {
        if (WTP.econ.getBalance(player) < WTPConfig.welcomeFee) {
            player.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
            return;
        }
        msgWait.put(player, warp);
        player.sendMessage(S.toPrefixYellow("���뻶ӭ��Ϣ ���� cancel ȡ��"));
    }

    private void onRelocate() {
        if (WTP.econ.getBalance(player) < WTPConfig.relocationFee) {
            player.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
            return;
        }
        WTP.econ.withdrawPlayer(player, WTPConfig.relocationFee);
        WTPData.relocate(warp, player);
        player.sendMessage(S.toPrefixGreen("�ɹ��ı䴫�͵㣡"));
    }

    private void onItem() {
        if (WTP.econ.getBalance(player) < WTPConfig.itemFee) {
            player.sendMessage(S.toPrefixRed("��Ľ�Ǯ����"));
            return;
        }
        itemWait.put(player, warp);
        player.sendMessage(S.toPrefixYellow("�Ҽ���Ʒ��������ʾ��Ʒ �Ҽ�������ȡ��"));
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
                    case (4):
                        onItem();
                        break;
                }
                player.closeInventory();
            }
        }
    }

}
