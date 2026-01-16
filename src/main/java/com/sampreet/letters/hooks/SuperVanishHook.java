package com.sampreet.letters.hooks;

import com.sampreet.letters.Letters;
import com.sampreet.letters.listeners.PlayerHideListener;
import com.sampreet.letters.listeners.PlayerShowListener;
import org.bukkit.Bukkit;

public class SuperVanishHook {
    // Prevent instantiation of the class
    private SuperVanishHook() {
    }

    // Function to check if SuperVanish or PremiumVanish is installed and print a
    // message
    public static void checkVanishPlugin(Letters plugin) {
        // Check if a supported vanish plugin is present
        boolean superVanishFound = Bukkit.getPluginManager().getPlugin("SuperVanish") != null;
        boolean premiumVanishFound = Bukkit.getPluginManager().getPlugin("PremiumVanish") != null;

        // Determine which vanish plugin was found
        String pluginName = null;

        if (superVanishFound) {
            pluginName = "SuperVanish";
        } else if (premiumVanishFound) {
            pluginName = "PremiumVanish";
        }

        // Only register the vanish event listeners if a vanish plugin is detected
        if (pluginName != null) {
            plugin.getServer().getPluginManager().registerEvents(new PlayerShowListener(plugin), plugin);
            plugin.getServer().getPluginManager().registerEvents(new PlayerHideListener(plugin), plugin);
        }

        // Store config message path depending on whether a vanish plugin is present
        String messagePath = (pluginName != null)
                ? "messages.system.checks.vanish-plugin-found"
                : "messages.system.checks.vanish-plugin-not-found";

        // Retrieve the message from the config
        String message = plugin.getConfig().getString(messagePath);

        // Return if the message is empty or null
        if (message == null || message.trim().isEmpty())
            return;

        // Log vanish plugin status
        if (pluginName != null)
            plugin.getLogger().info(message.replace("<vanish_plugin>", pluginName));
        else
            plugin.getLogger().warning(message);
    }
}
