/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP;

import cc.isotopestudio.WTP.commands.CommandW;
import cc.isotopestudio.WTP.commands.CommandWlist;
import cc.isotopestudio.WTP.commands.CommandWtp;
import cc.isotopestudio.WTP.commands.CommandWtpadmin;
import cc.isotopestudio.WTP.data.WTPConfig;
import cc.isotopestudio.WTP.listener.WaitListener;
import cc.isotopestudio.WTP.metrics.Metrics;
import cc.isotopestudio.WTP.tasks.UpdateWlist;
import cc.isotopestudio.WTP.tasks.Updater;
import cc.isotopestudio.WTP.util.PluginFile;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

public final class WTP extends JavaPlugin {
    public static final String version = "2.1.1";
    private static final String FileVersion = "2";
    public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append("[").append(ChatColor.ITALIC)
            .append(ChatColor.BOLD).append("�����ر�").append(ChatColor.RESET).append(ChatColor.GOLD).append("]")
            .append(ChatColor.RESET).toString();

    private static final Logger log = Logger.getLogger("Minecraft");
    public static Economy econ = null;

    public static WTP plugin;

    public static PluginFile config;
    public static PluginFile warpData;
    public static PluginFile playerData;

    public static CommandWlist commandWlist;

    @Override
    public void onEnable() {
        plugin = this;

        config = new PluginFile(this, "config.yml", "config.yml");
        config.setEditable(false);
        warpData = new PluginFile(this, "warps.yml");
        playerData = new PluginFile(this, "players.yml");

        getLogger().info("����Vault API");
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - �����ر��޷����룬ԭ��Vaultδ��װ", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("���������ļ���");

        if (!config.getString("FileVersion").equals(FileVersion)) {
            getLogger().warning("�����ر� �����ļ�����!");
            getLogger().warning("����������ļ�");
            getLogger().warning("�鿴 http://wiki.isotopestudio.cc/doku.php?id=wtp:config");
            onDisable();
            return;
        }

        WTPConfig.update();
        this.getCommand("w").setExecutor(new CommandW());
        commandWlist = new CommandWlist();
        this.getCommand("wlist").setExecutor(commandWlist);
        this.getCommand("wtp").setExecutor(new CommandWtp());
        this.getCommand("wtpadmin").setExecutor(new CommandWtpadmin());

        plugin.getServer().getPluginManager().registerEvents(new WaitListener(), plugin);

        UpdateWlist.updateWlist();
        new UpdateWlist().runTaskTimer(this, 3000, 3000);
        new Updater().start();

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getLogger().info("WTP �����ر� " + getDescription().getVersion() + "�ɹ�����!");
        getLogger().info("WTP �����ر� ��ISOTOPE Studio����!");
        getLogger().info("http://isotopestudio.cc");
    }

    public void onReload() {
        playerData.reload();
        warpData.reload();
        config.reload();
        UpdateWlist.updateWlist();
        WTPConfig.update();
    }

    @Override
    public void onDisable() {
        playerData.save();
        warpData.save();
        getLogger().info("�����ر� �ɹ�ж��!");
    }

    // Vault API
    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }
        return (econ != null);
    }

}
