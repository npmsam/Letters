package com.sampreet.letters.listeners;

import com.sampreet.letters.Letters;
import com.sampreet.letters.helpers.MessagesHelper;
import com.sampreet.letters.helpers.PlaceholdersHelper;
import com.sampreet.letters.hooks.PlaceholderApiHook;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AsyncChatListener implements Listener {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public AsyncChatListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncChat(AsyncChatEvent event) {
        // Retrieve a random message from config.yml
        String chatMessage = MessagesHelper.resolveRandomMessage(event.getPlayer(), "chat", plugin);

        // Insert PlaceholderAPI placeholders into the message
        chatMessage = PlaceholderApiHook.usePlaceholderAPI(event.getPlayer(), chatMessage);

        // Put colors into the message by translating & color codes and mini message
        Component chatMessageComponent = MessagesHelper.translateColors(chatMessage);

        // Insert placeholders into the message
        Component finalChatMessageComponent = PlaceholdersHelper.setPlaceholders(chatMessageComponent, event, plugin);

        // Set the custom message as the system message
        event.renderer((source, sourceDisplayName, message, viewer) -> finalChatMessageComponent);
    }
}
