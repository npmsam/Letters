package com.sampreet.letters.listeners;

import com.sampreet.letters.Letters;
import com.sampreet.letters.helpers.MessagesHelper;
import com.sampreet.letters.helpers.PlaceholdersHelper;
import com.sampreet.letters.hooks.PlaceholderApiHook;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerDeathListener implements Listener {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public PlayerDeathListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(@NotNull PlayerDeathEvent event) {
        // Retrieve a random message from config.yml
        String deathMessage = MessagesHelper.resolveRandomMessage(event.getPlayer(), "death", plugin);

        // Insert PlaceholderAPI placeholders into the message
        deathMessage = PlaceholderApiHook.usePlaceholderAPI(event.getPlayer(), deathMessage);

        // Put colors into the message by translating & color codes and mini message
        Component deathMessageComponent = MessagesHelper.translateColors(deathMessage);

        // Insert placeholders into the message
        deathMessageComponent = PlaceholdersHelper.setPlaceholders(deathMessageComponent, event, plugin);

        // Set the custom message as the system message
        event.deathMessage(deathMessageComponent);
    }
}
