package com.sampreet.letters.listeners;

import com.sampreet.letters.Letters;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

public class PlayerDeathListener implements Listener {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public PlayerDeathListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(@NonNull PlayerDeathEvent event) {
        // Check if the player has the permission to have custom death messages
        if (!event.getEntity().hasPermission("letters.death"))
            return;

        // Retrieve a random message from config.yml
        String deathMessage = plugin.getUtils().resolveRandomMessage(event.getEntity(), "death");
        // Insert placeholders and colors into the message
        deathMessage = plugin.getUtils().setPlaceholders(deathMessage, event);

        // Set the custom message as the system message
        event.setDeathMessage(deathMessage);
    }
}
