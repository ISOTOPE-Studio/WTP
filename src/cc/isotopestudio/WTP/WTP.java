/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP;

import cc.isotopestudio.WTP.commands.CommandWlist;
import cc.isotopestudio.WTP.commands.CommandWtp;
import cc.isotopestudio.WTP.tasks.UpdateWlist;
import cc.isotopestudio.WTP.commands.CommandW;
import cc.isotopestudio.WTP.commands.CommandWtpadmin;
import cc.isotopestudio.WTP.files.WTPConfig;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public final class WTP extends JavaPlugin {
    public static final String version = "v1.1";
    private static final String FileVersion = "1";
    public static final String prefix = (new StringBuilder()).append(ChatColor.GREEN).append("[").append(ChatColor.ITALIC)
            .append(ChatColor.BOLD).append("公共地标").append(ChatColor.RESET).append(ChatColor.GREEN).append("]")
            .append(ChatColor.RESET).toString();

    private static final Logger log = Logger.getLogger("Minecraft");
    public static Economy econ = null;

    public static WTP plugin;

    public CommandWlist commandWlist;

    @Override
    public void onEnable() {
        plugin = this;

        getLogger().info("加载Vault API");
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - 公共地标无法载入，原因：Vault未安装", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("加载配置文件中");

        createFile();
        if (!getConfig().getString("FileVersion").equals(FileVersion)) {
            getLogger().info("公共地标 配置文件错误!");
            onDisable();
            return;
        }

        try {
            getWarpsData().save(warpsFile);
            getPlayersData().save(playersFile);
        } catch (IOException ignored) {
        }

        // PluginManager pm = this.getServer().getPluginManager();
        WTPConfig.update(this);
        this.getCommand("w").setExecutor(new CommandW(this));
        commandWlist = new CommandWlist();
        this.getCommand("wlist").setExecutor(commandWlist);
        this.getCommand("wtp").setExecutor(new CommandWtp(this));
        this.getCommand("wtpadmin").setExecutor(new CommandWtpadmin(this));

        UpdateWlist.updateWlist(this);
        new UpdateWlist(this).runTaskTimer(this, 3000, 3000);

        getLogger().info("公共地标 成功加载!");
        getLogger().info("公共地标 由ISOTOPE Studio制作!");
        getLogger().info("http://isotopestudio.cc");
    }

    public void onReload() {
        reloadPlayersData();
        reloadWarpsData();
        this.reloadConfig();
        UpdateWlist.updateWlist(this);
        WTPConfig.update(this);
    }

    @Override
    public void onDisable() {
        savePlayersData();
        getLogger().info("公共地标 成功卸载!");
    }

    // Vault API
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    // Files
    private void createFile() {

        File file;
        file = new File(getDataFolder(), "config" + ".yml");
        if (!file.exists()) {
            saveDefaultConfig();
        }
    }

    private File playersFile = null;
    private FileConfiguration playersData = null;

    private void reloadPlayersData() {
        if (playersFile == null) {
            playersFile = new File(getDataFolder(), "players.yml");
        }
        playersData = YamlConfiguration.loadConfiguration(playersFile);
    }

    public FileConfiguration getPlayersData() {
        if (playersData == null) {
            reloadPlayersData();
        }
        return playersData;
    }

    public void savePlayersData() {
        if (playersData == null || playersFile == null) {
            return;
        }
        try {
            getPlayersData().save(playersFile);
        } catch (IOException ex) {
            getLogger().info("地标文件保存失败！");
        }
    }

    private File warpsFile = null;
    private FileConfiguration warpsData = null;

    private void reloadWarpsData() {
        if (warpsFile == null) {
            warpsFile = new File(getDataFolder(), "warps.yml");
        }
        warpsData = YamlConfiguration.loadConfiguration(warpsFile);
    }

    public FileConfiguration getWarpsData() {
        if (warpsData == null) {
            reloadWarpsData();
        }
        return warpsData;
    }

    public void saveWarpsData() {
        if (warpsData == null || warpsFile == null) {
            return;
        }
        try {
            getWarpsData().save(warpsFile);
        } catch (IOException ex) {
            getLogger().info("地标文件保存失败！");
        }
    }

}
