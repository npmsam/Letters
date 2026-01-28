package com.sampreet.letters.hooks;

import com.sampreet.letters.Letters;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderApiHook {
    private PlaceholderApiHook() {
    }

    public static String usePlaceholderAPI(Player player, String message) {
        if (message == null || message.trim().isEmpty())
            return null;

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null)
            return message;

        return PlaceholderAPI.setPlaceholders(player, message);
    }

    public static void checkPlaceholderAPI(@NotNull Letters plugin) {
        boolean PlaceholderApiFound = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;

        String messagePath = PlaceholderApiFound ?
                "system.dependencies.placeholder_api.found" :
                "system.dependencies.placeholder_api.not_found";

        String message = plugin.getConfig().getString(messagePath);

        if (message == null || message.trim().isEmpty())
            return;

        if (PlaceholderApiFound)
            plugin.getLogger().info(message);
        else
            plugin.getLogger().warning(message);
    }
}