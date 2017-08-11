/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.gui;

import cc.isotopestudio.WTP.data.WTPData;
import cc.isotopestudio.WTP.data.WTPPlayers;
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

public class WarpGUI extends GUI {

    public static Set<String> keys;

    private List<String> warps;
    private Map<Integer, String> slotIDMap;
    private List<String> favorites;

    public WarpGUI(Player player, int page) {
        super(S.toBoldGold("地标列表") + "[" + player.getName() + "]", 4 * 9, player);
        this.page = page;

        warps = new ArrayList<>(keys);
        slotIDMap = new HashMap<>();
        favorites = WTPPlayers.getPlayerFavoriteWarps(player.getName());

        if (page > 0) {
            setOption(0, new ItemStack(Material.ARROW), S.toBoldGold("上一页"), S.toRed("第 " + (page + 1) + " 页"));
            setOption(27, new ItemStack(Material.ARROW), S.toBoldGold("上一页"), S.toRed("第 " + (page + 1) + " 页"));
        }
        if (page < getTotalPage() - 1) {
            setOption(8, new ItemStack(Material.ARROW), S.toBoldGold("下一页"), S.toRed("第 " + (page + 1) + " 页"));
            setOption(35, new ItemStack(Material.ARROW), S.toBoldGold("下一页"), S.toRed("第 " + (page + 1) + " 页"));
        }
        for (int i = 0; i < 4 * 7; i++) {
            if (i + page * 4 * 7 < warps.size()) {
                int row = i / 7;
                int col = i % 7;
                int pos = row * 9 + col + 1;
                String warpName = warps.get(page * 4 * 7 + i);

                slotIDMap.put(pos, warpName);

                ItemStack warpItem = WTPData.getItem(warpName);
                ItemMeta itemMeta = warpItem.getItemMeta();
                itemMeta.setDisplayName(S.toBoldRed(warpName));
                List<String> lore = new ArrayList<>();
                lore.add(S.toGray("---------------"));
                String alias = WTPData.getAlias(warpName);
                if (alias != null) {
                    itemMeta.setDisplayName(S.toBoldDarkGreen(alias) + S.toGray(" (" + warpName + ")"));
//                  lore.add(S.toBoldDarkGreen(alias));
                }
                String msg = WTPData.getMsg(warpName);
                if (msg != null) {
                    lore.add(S.toGreen(msg));
                }
                lore.add(S.toGold("拥有者: " + WTPData.getOwner(warpName)));
                lore.add("");
                lore.add(S.toItalicGray("/w " + warpName));
                lore.add(S.toItalicYellow("单击 传送"));
                if (favorites.contains(warpName))
                    lore.add(S.toGold("个人收藏"));
                else
                    lore.add(S.toItalicYellow("Shift + 右键 添加至个人收藏"));
                itemMeta.setLore(lore);
                warpItem.setItemMeta(itemMeta);

                setOption(pos, warpItem);
            }
        }
    }

    private void onNextPage() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                (new WarpGUI(player, page + 1)).open(player);
            }
        }, 2);
    }

    private void onPreviousPage() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                (new WarpGUI(player, page - 1)).open(player);
            }
        }, 2);
    }

    private int getTotalPage() {
        int size = warps.size();
        int page = size / (7 * 4);
        if (size % (7 * 4) != 0)
            page++;
        return page;
    }

    private void onWarp(int slot) {
        String warpName = slotIDMap.get(slot);
        WTPPlayers.tpWarp(player, warpName);
    }

    private void onFavorite(int slot) {
        String warpName = slotIDMap.get(slot);
        if (favorites.contains(warpName))
            player.sendMessage(S.toPrefixRed(warpName + " 已经在个人收藏中"));
        else if (favorites.size() >= 42) {
            player.sendMessage(S.toPrefixRed("不能再添加更多的个人收藏了"));
        } else {
            WTPPlayers.addPlayerFavoriteWarp(player.getName(), warpName);
            player.sendMessage(S.toPrefixYellow("成功将 " + warpName + " 添加至个人收藏"));
        }
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
                if (slot == 0 || slot == 27) {
                    if (page > 0)
                        onPreviousPage();
                    else
                        return;
                } else if (slot == 8 || slot == 35) {
                    if (page < getTotalPage() - 1)
                        onNextPage();
                    else
                        return;
                } else if (slot % 9 > 0 && slot % 9 < 8) {
                    if (event.getClick() == ClickType.SHIFT_RIGHT)
                        onFavorite(slot);
                    else
                        onWarp(slot);
                }
                player.closeInventory();
            }
        }
    }

}
