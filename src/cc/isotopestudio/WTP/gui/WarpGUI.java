/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.gui;

import cc.isotopestudio.WTP.util.S;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static cc.isotopestudio.WTP.WTP.plugin;
import static cc.isotopestudio.WTP.WTP.warpData;

public class WarpGUI extends GUI {

    ArrayList<String> warps;
    Map<Integer, String> slotIDMap;

    public WarpGUI(Player player, int page) {
        super(S.toBoldGold("地标列表") + "[" + player.getName() + "]", 4 * 9, player);
        this.page = page;

        warps = new ArrayList<>(warpData.getKeys(false));
        slotIDMap = new HashMap<>();

        if (page > 0) {
            setOption(0, new ItemStack(Material.ARROW), S.toBoldGold("上一页"), S.toRed("第 " + (page + 1) + " 页"));
            setOption(27, new ItemStack(Material.ARROW), S.toBoldGold("上一页"), S.toRed("第 " + (page + 1) + " 页"));
        }
        if (page < getTotalPage() - 1) {
            setOption(8, new ItemStack(Material.ARROW), S.toBoldGold("下一页"), S.toRed("第 " + (page + 1) + " 页"));
            setOption(35, new ItemStack(Material.ARROW), S.toBoldGold("下一页"), S.toRed("第 " + (page + 1) + " 页"));
        }
        for (int i = 0; i < 4 * 7; i++) {
            if (i < warps.size()) {
                int row = i / 7;
                int col = i % 7;
                int pos = row * 9 + col + 1;
                slotIDMap.put(pos, warps.get(page * 4 * 7 + i));
                setOption(pos, new ItemStack(Material.GRASS), warps.get(page * 4 * 7 + i), "");
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
                    onWarp(slot);
                }
                player.closeInventory();
            }
        }
    }

}
