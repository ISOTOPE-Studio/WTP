/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP;

import cc.isotopestudio.WTP.commands.CommandW;
import cc.isotopestudio.WTP.commands.CommandWlist;
import cc.isotopestudio.WTP.commands.CommandWtp;
import cc.isotopestudio.WTP.commands.CommandWtpadmin;
import cc.isotopestudio.WTP.files.WTPConfig;
import cc.isotopestudio.WTP.tasks.UpdateWlist;
import cc.isotopestudio.WTP.util.PluginFile;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class WTP extends JavaPlugin {
    public static final String version = "v1.1.1";
    private static final String FileVersion = "1";
    public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append("[").append(ChatColor.ITALIC)
            .append(ChatColor.BOLD).append("公共地标").append(ChatColor.RESET).append(ChatColor.GOLD).append("]")
            .append(ChatColor.RESET).toString();

    private static final Logger log = Logger.getLogger("Minecraft");
    public static Economy econ = null;

    public static WTP plugin;

    public static PluginFile config;
    public static PluginFile warpData;
    public static PluginFile playerData;


    public CommandWlist commandWlist;

    @Override
    public void onEnable() {
        plugin = this;

        config = new PluginFile(this, "config.yml", "config.yml");
        config.setEditable(false);
        warpData = new PluginFile(this, "warps.yml");
        playerData = new PluginFile(this, "players.yml");

        getLogger().info("加载Vault API");
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - 公共地标无法载入，原因：Vault未安装", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("加载配置文件中");

        if (!getConfig().getString("FileVersion").equals(FileVersion)) {
            getLogger().info("公共地标 配置文件错误!");
            onDisable();
            return;
        }

        // PluginManager pm = this.getServer().getPluginManager();
        WTPConfig.update();
        this.getCommand("w").setExecutor(new CommandW());
        commandWlist = new CommandWlist();
        this.getCommand("wlist").setExecutor(commandWlist);
        this.getCommand("wtp").setExecutor(new CommandWtp());
        this.getCommand("wtpadmin").setExecutor(new CommandWtpadmin());

        UpdateWlist.updateWlist(this);
        new UpdateWlist(this).runTaskTimer(this, 3000, 3000);

        getLogger().info("公共地标 成功加载!");
        getLogger().info("公共地标 由ISOTOPE Studio制作!");
        getLogger().info("http://isotopestudio.cc");
    }

    public void onReload() {
        playerData.reload();
        warpData.reload();
        this.reloadConfig();
        UpdateWlist.updateWlist(this);
        WTPConfig.update();
    }

    @Override
    public void onDisable() {
        playerData.save();
        warpData.save();
        getLogger().info("公共地标 成功卸载!");
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
