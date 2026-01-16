package com.sampreet.letters;

import com.sampreet.letters.commands.LettersCommand;
import com.sampreet.letters.commands.WhisperCommand;
import com.sampreet.letters.hooks.PlaceholderApiHook;
import com.sampreet.letters.listeners.*;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Letters extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        // Save default config.yml to plugin folder
        saveDefaultConfig();

        // Check whether plugin is enabled or disabled in config.yml
        if (!getConfig().getBoolean("enabled", true)) {
            // Retrieve message from config and return if message is null
            String disabledMessage = getConfig().getString("messages.system.checks.plugin-disabled");
            if (disabledMessage != null && !disabledMessage.trim().isEmpty()) {
                // Insert current plugin version into placeholder
                disabledMessage = disabledMessage.replace("<plugin_version>", getDescription().getVersion());

                // Log that the plugin has successfully loaded and is ready
                getLogger().info(disabledMessage);
            }

            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Log whether PlaceholderAPI was found or not
        PlaceholderApiHook.checkPlaceholderAPI(this);

        // Register the letters command
        PluginCommand lettersPluginCommand = getCommand("letters");
        if (lettersPluginCommand != null) {
            // Set the command executor
            LettersCommand lettersCommand = new LettersCommand(this);
            lettersPluginCommand.setExecutor(lettersCommand);

            // Set the tab completer
            lettersPluginCommand.setTabCompleter(lettersCommand);
        }

        // Register the whisper command
        PluginCommand whisperPluginCommand = getCommand("tell");
        if (whisperPluginCommand != null) {
            // Set the command executor
            WhisperCommand whisperCommand = new WhisperCommand(this);
            whisperPluginCommand.setExecutor(whisperCommand);
        }

        // Register the event listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerAdvancementDoneListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new AsyncChatListener(this), this);

        // Retrieve message from config and return if message is null
        String enableMessage = getConfig().getString("messages.system.lifecycle.enable");
        if (enableMessage == null || enableMessage.trim().isEmpty())
            return;

        // Insert current plugin version into placeholder
        enableMessage = enableMessage.replace("<plugin_version>", getDescription().getVersion());

        // Log that the plugin has successfully loaded and is ready
        getLogger().info(enableMessage);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // Retrieve message from config and return if message is null
        String disableMessage = getConfig().getString("messages.system.lifecycle.disable");
        if (disableMessage == null || disableMessage.trim().isEmpty())
            return;

        // Insert current plugin version into placeholder
        disableMessage = disableMessage.replace("<plugin_version>", getDescription().getVersion());

        // Log that the plugin has been disabled
        getLogger().info(disableMessage);
    }
}
