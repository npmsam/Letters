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
    private final Letters plugin;

    public AsyncChatListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncChat(AsyncChatEvent asyncChatEvent) {
        String chatMessage = MessagesHelper.resolveRandomMessage(asyncChatEvent.getPlayer(), "chat", plugin);

        chatMessage = PlaceholderApiHook.usePlaceholderAPI(asyncChatEvent.getPlayer(), chatMessage);
        Component chatMessageComponent = MessagesHelper.translateColors(chatMessage);
        Component finalChatMessageComponent = PlaceholdersHelper.setPlaceholders(chatMessageComponent, asyncChatEvent, plugin);

        if (finalChatMessageComponent == null || finalChatMessageComponent == Component.empty()) {
            asyncChatEvent.setCancelled(true);
            return;
        }

        asyncChatEvent.renderer((source, sourceDisplayName, message, viewer) -> finalChatMessageComponent);
    }
}
