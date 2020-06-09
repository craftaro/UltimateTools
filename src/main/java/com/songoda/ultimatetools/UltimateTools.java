package com.songoda.ultimatetools;

import com.songoda.core.SongodaPlugin;
import com.songoda.core.commands.CommandManager;
import com.songoda.core.configuration.Config;
import com.songoda.core.locale.Locale;
import com.songoda.ultimatetools.commands.CommandGiveBook;
import com.songoda.ultimatetools.enchant.EnchantManager;
import com.songoda.ultimatetools.listeners.BlockListeners;
import com.songoda.ultimatetools.listeners.EntityListeners;
import com.songoda.ultimatetools.listeners.InteractListeners;
import com.songoda.ultimatetools.listeners.InventoryListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.List;

public class UltimateTools extends SongodaPlugin {

    private static UltimateTools INSTANCE;

    private CommandManager commandManager;
    private EnchantManager enchantManager;

    public UltimateTools getInstance() {
        return INSTANCE;
    }

    @Override
    public void onPluginLoad() {
        INSTANCE = this;
    }

    @Override
    public void onPluginEnable() {

        // Register commands
        this.commandManager = new CommandManager(this);
        this.commandManager.addMainCommand("UTO")
                .addSubCommands(
                        new CommandGiveBook(this));

        this.enchantManager = new EnchantManager(this).load();

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new InteractListeners(this), this);
        pluginManager.registerEvents(new BlockListeners(this), this);
        pluginManager.registerEvents(new EntityListeners(this), this);
        pluginManager.registerEvents(new InventoryListeners(this), this);

    }

    @Override
    public void onPluginDisable() {

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
