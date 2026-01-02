package com.sampreet.letters.listeners;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.EventHandler;
import com.sampreet.letters.Letters;
import org.bukkit.event.Listener;

public class AsyncPlayerChatEventListener implements Listener {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public AsyncPlayerChatEventListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(@NonNull AsyncPlayerChatEvent event) {
        // Check if the player has the permission to have custom server chat messages
        if (!event.getPlayer().hasPermission("letters.chat"))
            return;

        // Retrieve a random message from config.yml
        String chatMessage = plugin.getUtils().resolveRandomMessage(event.getPlayer(), "chat");
        // Insert placeholders and colors into the message
        chatMessage = plugin.getUtils().setPlaceholders(chatMessage, event);

        // Broadcast the custom message to the server
        event.setFormat(chatMessage);
    }
}
