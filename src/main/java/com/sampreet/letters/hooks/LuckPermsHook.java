package com.sampreet.letters.hooks;

import com.sampreet.letters.Letters;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class LuckPermsHook {
    private LuckPermsHook() {
    }

    public static void checkLuckPerms(Letters plugin) {
        boolean LuckPermsFound = Bukkit.getPluginManager().getPlugin("LuckPerms") != null;

        String messagePath = LuckPermsFound
                ? "system_messages.dependencies.luckperms.found"
                : "system_messages.dependencies.luckperms.not_found";

        String message = plugin.getConfig().getString(messagePath);

        if (message == null || message.trim().isEmpty())
            return;

        if (LuckPermsFound)
            plugin.getLogger().info(message);
        else
            plugin.getLogger().warning(message);
    }

    public static @Nullable String getPrimaryGroup(Player player) {
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") == null)
            return null;

        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());

        if (user == null)
            return null;

        return user.getPrimaryGroup().toLowerCase();
    }
}
