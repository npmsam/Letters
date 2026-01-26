package com.sampreet.letters.hooks;

import com.sampreet.letters.Letters;
import com.sampreet.letters.listeners.PlayerHideListener;
import com.sampreet.letters.listeners.PlayerShowListener;
import org.bukkit.Bukkit;

public class VanishHook {
    private VanishHook() {
    }

    public static void checkVanishPlugin(Letters plugin) {
        boolean superVanishFound = Bukkit.getPluginManager().getPlugin("SuperVanish") != null;
        boolean premiumVanishFound = Bukkit.getPluginManager().getPlugin("PremiumVanish") != null;

        String pluginName = null;

        if (superVanishFound) {
            pluginName = "SuperVanish";
        } else if (premiumVanishFound) {
            pluginName = "PremiumVanish";
        }

        if (pluginName != null) {
            plugin.getServer().getPluginManager().registerEvents(new PlayerShowListener(plugin), plugin);
            plugin.getServer().getPluginManager().registerEvents(new PlayerHideListener(plugin), plugin);
        }

        String messagePath = (pluginName != null) ?
                "system.dependencies.vanish.found" :
                "system.dependencies.vanish.not_found";

        String message = plugin.getConfig().getString(messagePath);

        if (message == null || message.trim().isEmpty())
            return;

        if (pluginName != null)
            plugin.getLogger().info(message.replace("<vanish_plugin>", pluginName));
        else
            plugin.getLogger().warning(message);
    }
}