package com.sampreet.letters.listeners;

import com.sampreet.letters.Letters;
import com.sampreet.letters.helpers.MessagesHelper;
import com.sampreet.letters.helpers.PlaceholdersHelper;
import com.sampreet.letters.hooks.PlaceholderAPIHook;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public PlayerJoinListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Retrieve a random message from config.yml
        String joinMessage = MessagesHelper.getRandomMessage("messages.default.join", plugin);

        // Insert PlaceholderAPI placeholders into the message
        joinMessage = PlaceholderAPIHook.usePlaceholderAPI(event.getPlayer(), joinMessage);

        // Put colors into the message by translating & color codes and mini message
        Component joinMessageComponent = MessagesHelper.translateColors(joinMessage);

        // Insert placeholders into the message
        joinMessageComponent = PlaceholdersHelper.setPlaceholders(joinMessageComponent, event, plugin);

        // Set the custom message as the system message
        event.joinMessage(joinMessageComponent);
    }
}
