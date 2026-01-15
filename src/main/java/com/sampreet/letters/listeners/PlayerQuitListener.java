package com.sampreet.letters.listeners;

import com.sampreet.letters.Letters;
import com.sampreet.letters.helpers.MessagesHelper;
import com.sampreet.letters.helpers.PlaceholdersHelper;
import com.sampreet.letters.hooks.PlaceholderAPIHook;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public PlayerQuitListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuitJoin(PlayerQuitEvent event) {
        // Retrieve a random message from config.yml
        String quitMessage = MessagesHelper.getRandomMessage("messages.default.quit", plugin);

        // Insert PlaceholderAPI placeholders into the message
        quitMessage = PlaceholderAPIHook.usePlaceholderAPI(event.getPlayer(), quitMessage);

        // Put colors into the message by translating & color codes and mini message
        Component quitMessageComponent = MessagesHelper.translateColors(quitMessage);

        // Insert placeholders into the message
        quitMessageComponent = PlaceholdersHelper.setPlaceholders(quitMessageComponent, event, plugin);

        // Set the custom message as the system message
        event.quitMessage(quitMessageComponent);
    }
}
