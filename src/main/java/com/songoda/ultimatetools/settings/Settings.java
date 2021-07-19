package com.songoda.ultimatetools.settings;

import com.songoda.core.configuration.Config;
import com.songoda.core.configuration.ConfigSetting;
import com.songoda.ultimatetools.UltimateTools;

import java.util.Arrays;

public class Settings {

    static final Config config = UltimateTools.getInstance().getCoreConfig();

    public static final ConfigSetting REMOTE_TOOLS_BLACKLIST = new ConfigSetting(config, "Main.Sync Touch Blacklist",
            Arrays.asList("CHEST", "FURNACE", "HOPPER"),
            "Items that shouldn't work with Remote Tools. You should put items in here",
            "that other plugins use to store data with.");

    public static final ConfigSetting LANGUGE_MODE = new ConfigSetting(config, "System.Language Mode", "en_US",
            "The enabled language file.",
            "More language files (if available) can be found in the plugins data folder.");

    /**
     * In order to set dynamic economy comment correctly, this needs to be
     * called after EconomyManager load
     */
    public static void setupConfig() {
        config.load();
        config.setAutoremove(true).setAutosave(true);
        config.saveChanges();
    }
}
