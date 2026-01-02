package com.sampreet.letters.lib;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

public class LuckPermsHook {
    // Store luckperms singleton for accessing config
    private final LuckPerms luckPerms;

    public LuckPermsHook() {
        this.luckPerms = LuckPermsProvider.get();
    }

    // Helper function to get the primary group of a player
    public String playerPrimaryGroup(Player player) {
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user == null) return null;

        return user.getPrimaryGroup().toLowerCase();
    }
}
