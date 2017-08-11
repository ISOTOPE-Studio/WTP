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
        super(S.toBoldGold("地标管理") + "[" + warp+ "]", 9, player);
        this.page = 0;
        this.warp = warp;
        setOption(0, new ItemStack(Material.BOOK_AND_QUILL), S.toBoldGold(warp), S.toYellow("别名: " + WTPData.getAlias(warp)), S.toYellow("欢迎信息: " + WTPData.getMsg(warp)));

        setOption(1, new ItemStack(Material.NAME_TAG), S.toBoldGold("重命名"), S.toYellow("花费" + WTPConfig.aliasFee + "给公共地标添加别名"));
        setOption(2, new ItemStack(Material.PAPER), S.toBoldGold("设置欢迎信息"), S.toYellow("花费" + WTPConfig.welcomeFee + "给公共地标添加欢迎信息"));
        setOption(3, new ItemStack(Material.COMPASS), S.toBoldGold("重设位置"), S.toYellow("花费" + WTPConfig.relocationFee + "更改公共地标为当前位置"));
        setOption(4, new ItemStack(Material.DIAMOND_SWORD), S.toBoldGold("设置物品"), S.toYellow("花费" + WTPConfig.itemFee + "设置GUI中显示的物品"));

    }

    private void onRename() {
        if (WTP.econ.getBalance(player) < WTPConfig.aliasFee) {
            player.sendMessage(S.toPrefixRed("你的金钱不足"));
            return;
        }
        renameWait.put(player, warp);
        player.sendMessage(S.toPrefixYellow("输入别名 输入 cancel 取消"));
    }

    private void onMsg() {
        if (WTP.econ.getBalance(player) < WTPConfig.welcomeFee) {
            player.sendMessage(S.toPrefixRed("你的金钱不足"));
            return;
        }
        msgWait.put(player, warp);
        player.sendMessage(S.toPrefixYellow("输入欢迎信息 输入 cancel 取消"));
    }

    private void onRelocate() {
        if (WTP.econ.getBalance(player) < WTPConfig.relocationFee) {
            player.sendMessage(S.toPrefixRed("你的金钱不足"));
            return;
        }
        WTP.econ.withdrawPlayer(player, WTPConfig.relocationFee);
        WTPData.relocate(warp, player);
        player.sendMessage(S.toPrefixGreen("成功改变传送点！"));
    }

    private void onItem() {
        if (WTP.econ.getBalance(player) < WTPConfig.itemFee) {
            player.sendMessage(S.toPrefixRed("你的金钱不足"));
            return;
        }
        itemWait.put(player, warp);
        player.sendMessage(S.toPrefixYellow("右键物品来设置显示物品 右键空气以取消"));
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
