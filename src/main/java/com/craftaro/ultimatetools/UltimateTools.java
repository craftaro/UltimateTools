package com.craftaro.ultimatetools;

import com.craftaro.core.SongodaCore;
import com.craftaro.core.SongodaPlugin;
import com.craftaro.core.commands.CommandManager;
import com.craftaro.core.third_party.com.cryptomorin.xseries.XMaterial;
import com.craftaro.core.configuration.Config;
import com.craftaro.core.locale.Locale;
import com.craftaro.ultimatetools.commands.CommandGiveBook;
import com.craftaro.ultimatetools.enchant.EnchantManager;
import com.craftaro.ultimatetools.listeners.BlockListeners;
import com.craftaro.ultimatetools.listeners.EntityListeners;
import com.craftaro.ultimatetools.listeners.InteractListeners;
import com.craftaro.ultimatetools.listeners.InventoryListeners;
import com.craftaro.ultimatetools.listeners.VanillaEnchantListener;
import com.craftaro.ultimatetools.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.List;

public class UltimateTools extends SongodaPlugin {
    private static UltimateTools INSTANCE;

    private EnchantManager enchantManager;

    public static UltimateTools getInstance() {
        return INSTANCE;
    }

    @Override
    public void onPluginLoad() {
        INSTANCE = this;
    }

    @Override
    public void onPluginEnable() {
        // Run Craftaro Updater
        SongodaCore.registerPlugin(this, 580, XMaterial.ENCHANTED_BOOK);

        // Setup Config
        Settings.setupConfig();
        this.setLocale(Settings.LANGUGE_MODE.getString(), false);

        // Register commands
        CommandManager commandManager = new CommandManager(this);
        commandManager.addMainCommand("UTO")
                .addSubCommands(new CommandGiveBook(this));

        this.enchantManager = new EnchantManager(this).load();

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new InteractListeners(this), this);
        pluginManager.registerEvents(new BlockListeners(this), this);
        pluginManager.registerEvents(new EntityListeners(this), this);
        pluginManager.registerEvents(new InventoryListeners(this), this);
        pluginManager.registerEvents(new VanillaEnchantListener(this), this);
    }

    @Override
    public void onPluginDisable() {
    }

    @Override
    public void onDataLoad() {
    }

    @Override
    public void onConfigReload() {
    }

    @Override
    public List<Config> getExtraConfig() {
        return null;
    }

    public EnchantManager getEnchantManager() {
        return enchantManager;
    }

    public Locale getLocale() {
        return locale;
    }
}
