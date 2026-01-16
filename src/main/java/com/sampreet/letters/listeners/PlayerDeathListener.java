package com.sampreet.letters.listeners;

import com.sampreet.letters.Letters;
import com.sampreet.letters.helpers.MessagesHelper;
import com.sampreet.letters.helpers.PlaceholdersHelper;
import com.sampreet.letters.hooks.PlaceholderApiHook;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public PlayerDeathListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // Retrieve a random message from config.yml
        String deathMessage = MessagesHelper.getRandomMessage("messages.default.death", plugin);

        // Insert PlaceholderAPI placeholders into the message
        deathMessage = PlaceholderApiHook.usePlaceholderAPI(event.getPlayer(), deathMessage);

        // Put colors into the message by translating & color codes and mini message
        Component joinMessageComponent = MessagesHelper.translateColors(deathMessage);

        // Insert placeholders into the message
        joinMessageComponent = PlaceholdersHelper.setPlaceholders(joinMessageComponent, event, plugin);

        // Set the custom message as the system message
        event.deathMessage(joinMessageComponent);
    }
}
