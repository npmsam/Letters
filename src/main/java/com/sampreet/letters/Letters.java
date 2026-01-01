package com.sampreet.letters;

import com.sampreet.letters.commands.WhisperCommand;
import com.sampreet.letters.commands.RootCommand;
import org.bukkit.plugin.java.JavaPlugin;
import com.sampreet.letters.listeners.*;
import com.sampreet.letters.lib.Utils;
import java.util.Objects;

public final class Letters extends JavaPlugin {
    // Store utils class instance for accessing helper functions
    private Utils utils;

    @Override
    public void onEnable() {
        // Save default config.yml to plugin folder
        saveDefaultConfig();

        // Check whether plugin is enabled or disabled in config.yml
        if (!getConfig().getBoolean("enabled", true)) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Initialize the Utils class with the plugin instance
        utils = new Utils(this);

        // Register the root command executor
        RootCommand rootCommand = new RootCommand(this);
        Objects.requireNonNull(this.getCommand("letters")).setExecutor(rootCommand);
        // Register the root command tab completer
        Objects.requireNonNull(this.getCommand("letters")).setTabCompleter(rootCommand);

        // Register the whisper command executor
        WhisperCommand whisperCommand = new WhisperCommand(this);
        Objects.requireNonNull(this.getCommand("msg")).setExecutor(whisperCommand);
        // Register the whisper command tab completer
        Objects.requireNonNull(this.getCommand("msg")).setTabCompleter(whisperCommand);

        // Register the event listeners
        getServer().getPluginManager().registerEvents(new PlayerAdvancementDoneListener(this), this);
        getServer().getPluginManager().registerEvents(new AsyncPlayerChatEventListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);

        // Log that the plugin has successfully loaded and is ready
        String enableMessage = utils.getMessage("messages.system.lifecycle.enable");
        if (enableMessage!=null) {
            // Insert current plugin version into placeholder
            enableMessage = utils.setPlaceholders(enableMessage);
            getLogger().info(enableMessage);
        }
    }

    @Override
    public void onDisable() {
        // Log that the plugin has been disabled
        String disableMessage = utils.getMessage("messages.system.lifecycle.disable");
        if (disableMessage!=null) {
            // Insert current plugin version into placeholder
            disableMessage = utils.setPlaceholders(disableMessage);
            getLogger().info(disableMessage);
        }
    }

    // Getter function to make utils available outside main class
    public Utils getUtils() {
        return utils;
    }
}
