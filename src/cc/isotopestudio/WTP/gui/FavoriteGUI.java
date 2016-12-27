/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.gui;

import cc.isotopestudio.WTP.files.WTPData;
import cc.isotopestudio.WTP.files.WTPPlayers;
import cc.isotopestudio.WTP.util.S;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static cc.isotopestudio.WTP.WTP.plugin;

public class FavoriteGUI extends GUI {

    private Map<Integer, String> slotIDMap;

    public FavoriteGUI(Player player) {
        super(S.toBoldGold("收藏地标列表") + "[" + player.getName() + "]", 6 * 9, player);
        this.page = 0;

        List<String> warps = new ArrayList<>(WTPPlayers.getPlayerFavoriteWarps(player.getName()));
        slotIDMap = new HashMap<>();

        setOption(0, new ItemStack(Material.ARROW), S.toBoldGold("查看所有地标"), S.toRed(""));
        setOption(45, new ItemStack(Material.ARROW), S.toBoldGold("查看所有地标"), S.toRed(""));
        setOption(8, new ItemStack(Material.ARROW), S.toBoldGold("查看所有地标"), S.toRed(""));
        setOption(53, new ItemStack(Material.ARROW), S.toBoldGold("查看所有地标"), S.toRed(""));

        for (int i = 0; i < 6 * 7; i++) {
            if (i + page * 6 * 7 < warps.size()) {
                int row = i / 7;
                int col = i % 7;
                int pos = row * 9 + col + 1;
                String warpName = warps.get(page * 6 * 7 + i);

                slotIDMap.put(pos, warpName);

                ItemStack warpItem = new ItemStack(Material.WOOL, 1, (short) (Math.random() * 16));
                ItemMeta itemMeta = warpItem.getItemMeta();
                itemMeta.setDisplayName(S.toBoldRed(warpName));
                List<String> lore = new ArrayList<>();
                String alias = WTPData.getAlias(warpName);
                if (alias != null) {
                    lore.add(S.toBoldDarkGreen(alias));
                }
                String msg = WTPData.getMsg(warpName);
                if (msg != null) {
                    lore.add(S.toGreen(msg));
                }
                lore.add(S.toGold("拥有者: " + WTPData.getOwner(warpName)));
                lore.add("");
                lore.add(S.toItalicYellow("单击 传送"));
                lore.add(S.toItalicYellow("Shift + 右键 从个人收藏中删除"));
                itemMeta.setLore(lore);
                warpItem.setItemMeta(itemMeta);

                setOption(pos, warpItem);
            }
        }
    }

    private void onDisplayAllWarp() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                (new WarpGUI(player, 0)).open(player);
            }
        }, 2);
    }


    private void onWarp(int slot) {
        String warpName = slotIDMap.get(slot);
        WTPPlayers.tpWarp(player, warpName);
    }

    private void onDisfavorite(int slot) {
        String warpName = slotIDMap.get(slot);
        WTPPlayers.removePlayerFavoriteWarp(player.getName(), warpName);
        player.sendMessage(S.toPrefixYellow("成功将 " + warpName + " 从个人收藏中删除"));
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
                if (slot == 0 || slot == 45 || slot == 8 || slot == 53) {
                    onDisplayAllWarp();
                } else if (slot % 9 > 0 && slot % 9 < 8) {
                    if (event.getClick() == ClickType.SHIFT_RIGHT)
                        onDisfavorite(slot);
                    else
                        onWarp(slot);
                }
                player.closeInventory();
            }
        }
    }

}
