/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.gui;

import cc.isotopestudio.WTP.WTP;
import cc.isotopestudio.WTP.data.WTPConfig;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cc.isotopestudio.WTP.WTP.plugin;
import static cc.isotopestudio.WTP.listener.WaitListener.createWait;
import static cc.isotopestudio.WTP.listener.WaitListener.renameWait;

public class FavoriteGUI extends GUI {

    private final Map<Integer, String> slotIDMap;
    private List<String> favoriteWarps;
    private List<String> playerWarps;

    public FavoriteGUI(Player player) {
        this(player, 0);
    }

    private FavoriteGUI(Player player, int page) {
        super(S.toBoldGold("收藏地标列表") + "[" + player.getName() + "]", 6 * 9, player);
        this.page = page;

        if (page < 1)
            favoriteWarps = WTPPlayers.getPlayerFavoriteWarps(player.getName());
        else {
            playerWarps = WTPPlayers.getPlayerWarpsList(player.getName());
            setName(S.toBoldGold(" 我的地标") + "[" + player.getName() + "]");
        }
        slotIDMap = new HashMap<>();

        setOption(0, new ItemStack(Material.COMPASS), S.toBoldGold("查看所有地标"), S.toRed(""));
        setOption(8, new ItemStack(Material.COMPASS), S.toBoldGold("查看所有地标"), S.toRed(""));
        setOption(18, new ItemStack(Material.PAPER), S.toBoldGold("创建新的地标"), S.toRed(""));
        setOption(27, new ItemStack(Material.PAPER), S.toBoldGold("创建新的地标"), S.toRed(""));
        setOption(26, new ItemStack(Material.PAPER), S.toBoldGold("创建新的地标"), S.toRed(""));
        setOption(35, new ItemStack(Material.PAPER), S.toBoldGold("创建新的地标"), S.toRed(""));

        if (page > 0)
            setOption(45, new ItemStack(Material.ARROW), S.toBoldGold("上一页"), S.toRed(""));
        if (page < getTotalPage() - 1)
            setOption(53, new ItemStack(Material.ARROW), S.toBoldGold("下一页"), S.toRed(""));

        if (page == 1) {
            setOption(45, new ItemStack(Material.ARROW), S.toBoldGold("我的收藏"), S.toRed(""));
        }
        if (page == 0) {
            setOption(53, new ItemStack(Material.ARROW), S.toBoldGold("我的地标"), S.toRed(""));
        }

        for (int i = 0; i < 6 * 7; i++) {
            if (page < 1) {
                // Favorites page
                if (i + page * 6 * 7 < favoriteWarps.size()) {
                    int row = i / 7;
                    int col = i % 7;
                    int pos = row * 9 + col + 1;
                    String warpName = favoriteWarps.get(page * 6 * 7 + i);

                    slotIDMap.put(pos, warpName);

                    ItemStack warpItem = WTPData.getItem(warpName);
                    ItemMeta itemMeta = warpItem.getItemMeta();
                    itemMeta.setDisplayName(S.toBoldRed(warpName));
                    List<String> lore = new ArrayList<>();
                    lore.add(S.toGray("---------------"));
                    String alias = WTPData.getAlias(warpName);
                    if (alias != null) {
                        itemMeta.setDisplayName(S.toBoldDarkGreen(alias) + S.toGray(" (" + warpName + ")"));
//                      lore.add(S.toBoldDarkGreen(alias));
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
                } else break;
            } else {
                // My warps page
                if (i + (page - 1) * 6 * 7 < playerWarps.size()) {
                    int row = i / 7;
                    int col = i % 7;
                    int pos = row * 9 + col + 1;
                    String warpName = playerWarps.get((page - 1) * 6 * 7 + i);

                    slotIDMap.put(pos, warpName);

                    ItemStack warpItem = WTPData.getItem(warpName);
                    ItemMeta itemMeta = warpItem.getItemMeta();
                    itemMeta.setDisplayName(S.toBoldRed(warpName));
                    List<String> lore = new ArrayList<>();
                    lore.add(S.toGray("---------------"));
                    String alias = WTPData.getAlias(warpName);
                    if (alias != null) {
                        itemMeta.setDisplayName(S.toBoldDarkGreen(alias) + S.toGray(" (" + warpName + ")"));
//                      lore.add(S.toBoldDarkGreen(alias));
                    }
                    String msg = WTPData.getMsg(warpName);
                    if (msg != null) {
                        lore.add(S.toGreen(msg));
                    }
                    lore.add(S.toGold("拥有的地标"));
                    lore.add("");
                    lore.add(S.toItalicYellow("单击 传送"));
                    lore.add(S.toItalicYellow("Shift + 右键 管理此地标"));
                    itemMeta.setLore(lore);
                    warpItem.setItemMeta(itemMeta);

                    setOption(pos, warpItem);
                } else break;
            }
        }
    }

    private void onNextPage() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                (new FavoriteGUI(player, page + 1)).open(player);
            }
        }, 2);
    }

    private void onPreviousPage() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                (new FavoriteGUI(player, page - 1)).open(player);
            }
        }, 2);
    }

    private int getTotalPage() {
        if (playerWarps == null) {
            return 2;
        }
        int size = playerWarps.size();
        int page = size / (7 * 4);
        if (size % (7 * 4) != 0)
            page++;
        return page + 1;
    }

    private void onDisplayAllWarp() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                (new WarpGUI(player, 0)).open(player);
            }
        }, 2);
    }

    private void onNewWarp() {
        if (WTP.econ.getBalance(player) < WTPConfig.createFee) {
            player.sendMessage(S.toPrefixRed("你的金钱不足"));
            return;
        }
        createWait.add(player);
        player.sendMessage(S.toPrefixYellow("输入地标名 输入 cancel 取消"));
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

    private void onManage(int slot) {
        final String warpName = slotIDMap.get(slot);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                new ManagerGUI(player, warpName).open(player);
            }
        }, 2);
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
                if (slot == 0 || slot == 8) {
                    onDisplayAllWarp();
                } else if (slot == 45) {
                    if (page > 0)
                        onPreviousPage();
                    else
                        return;
                } else if (slot == 53) {
                    if (page < getTotalPage() - 1)
                        onNextPage();
                    else
                        return;
                } else if (slot == 18 || slot == 27 || slot == 26 || slot == 35) {
                    onNewWarp();
                } else if (slot % 9 > 0 && slot % 9 < 8) {
                    if (event.getClick() == ClickType.SHIFT_RIGHT) {
                        if (page < 1) {
                            onDisfavorite(slot);
                        } else {
                            onManage(slot);
                        }
                    } else
                        onWarp(slot);
                }
                player.closeInventory();
            }
        }
    }

}

