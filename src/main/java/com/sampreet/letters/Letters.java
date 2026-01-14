package com.sampreet.letters;

import com.sampreet.letters.commands.LettersCommand;
import com.sampreet.letters.helpers.PlaceholdersHelper;
import com.sampreet.letters.listeners.PlayerJoinListener;
import com.sampreet.letters.listeners.PlayerQuitListener;
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
                disabledMessage = PlaceholdersHelper.setPlaceholders(disabledMessage, this);

                // Log that the plugin has successfully loaded and is ready
                getLogger().info(disabledMessage);
            }

            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Register the root command
        PluginCommand lettersPluginCommand = getCommand("letters");
        if (lettersPluginCommand != null) {
            // Set the command executor
            LettersCommand lettersCommand = new LettersCommand(this);
            lettersPluginCommand.setExecutor(lettersCommand);

            // Set the tab completer
            lettersPluginCommand.setTabCompleter(lettersCommand);
        }

        // Register the event listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);

        // Retrieve message from config and return if message is null
        String enableMessage = getConfig().getString("messages.system.lifecycle.enable");
        if (enableMessage == null || enableMessage.trim().isEmpty()) return;

        // Insert current plugin version into placeholder
        enableMessage = PlaceholdersHelper.setPlaceholders(enableMessage, this);

        // Log that the plugin has successfully loaded and is ready
        getLogger().info(enableMessage);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // Retrieve message from config and return if message is null
        String disableMessage = getConfig().getString("messages.system.lifecycle.disable");
        if (disableMessage == null || disableMessage.trim().isEmpty()) return;

        // Insert current plugin version into placeholder
        disableMessage = PlaceholdersHelper.setPlaceholders(disableMessage, this);

        // Log that the plugin has been disabled
        getLogger().info(disableMessage);
    }
}
