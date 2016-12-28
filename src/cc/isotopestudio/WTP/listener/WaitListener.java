/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.listener;

import cc.isotopestudio.WTP.WTP;
import cc.isotopestudio.WTP.data.WTPConfig;
import cc.isotopestudio.WTP.data.WTPData;
import cc.isotopestudio.WTP.util.S;
import net.milkbowl.vault.economy.EconomyResponse;
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
import java.util.Map;

public class WaitListener implements Listener {

    public static Map<Player, String> renameWait = new HashMap<>();
    public static Map<Player, String> msgWait = new HashMap<>();
    public static Map<Player, String> itemWait = new HashMap<>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        String msg = ChatColor.stripColor(event.getMessage());
        if (renameWait.containsKey(player)) {
            event.setCancelled(true);
            if (msg.contains("cancel")) {
                player.sendMessage(S.toPrefixRed("取消操作"));
                return;
            }
            if (msg.length() >= 15) {
                player.sendMessage(S.toPrefixRed("别名不能超过15个字"));
                return;
            }
            if (WTP.econ.getBalance(player.getName()) < WTPConfig.aliasFee) {
                player.sendMessage(S.toPrefixRed("你的金钱不足"));
                return;
            }
            WTP.econ.withdrawPlayer(player.getName(), WTPConfig.aliasFee);
            WTPData.setAlias(renameWait.remove(player), ChatColor.stripColor(msg));
            player.sendMessage(S.toPrefixGreen("成功添加别名！"));
        } else if (msgWait.containsKey(player)) {
            event.setCancelled(true);
            if (msg.contains("cancel")) {
                player.sendMessage(S.toPrefixRed("取消操作"));
                return;
            }
            if (msg.length() >= 30) {
                player.sendMessage(
                        S.toPrefixRed("欢迎信息不能超过30个字"));
                return;
            }
            if (WTP.econ.getBalance(player.getName()) < WTPConfig.welcomeFee) {
                player.sendMessage(S.toPrefixRed("你的金钱不足"));
                return;
            }
            WTP.econ.withdrawPlayer(player.getName(), WTPConfig.welcomeFee);
            WTPData.setMsg(msgWait.remove(player), msg);
            player.sendMessage(S.toPrefixGreen("成功添加欢迎信息！"));
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
                    player.sendMessage(S.toPrefixRed("取消操作"));
                    return;
                }
                if (WTP.econ.getBalance(player.getName()) < WTPConfig.itemFee) {
                    player.sendMessage(S.toPrefixRed("你的金钱不足"));
                    return;
                }
                WTP.econ.withdrawPlayer(player.getName(), WTPConfig.itemFee);
                WTPData.setItem(itemWait.remove(player), item);
                player.sendMessage(S.toPrefixGreen("成功添加物品！"));
            }
        }
    }
}
