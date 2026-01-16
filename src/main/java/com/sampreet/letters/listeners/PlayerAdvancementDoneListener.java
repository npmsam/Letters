package com.sampreet.letters.listeners;

import com.sampreet.letters.Letters;
import com.sampreet.letters.helpers.MessagesHelper;
import com.sampreet.letters.helpers.PlaceholdersHelper;
import com.sampreet.letters.hooks.PlaceholderApiHook;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerAdvancementDoneListener implements Listener {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public PlayerAdvancementDoneListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerAdvancementDone(@NotNull PlayerAdvancementDoneEvent event) {
        // Return if the advancement has no display
        if (event.getAdvancement().getDisplay() == null || event.getAdvancement().getParent() == null)
            return;

        // Retrieve a random message from config.yml
        String advancementMessage = MessagesHelper.resolveRandomMessage(event.getPlayer(), "advancement", plugin);

        // Insert PlaceholderAPI placeholders into the message
        advancementMessage = PlaceholderApiHook.usePlaceholderAPI(event.getPlayer(), advancementMessage);

        // Put colors into the message by translating & color codes and mini message
        Component advancementMessageComponent = MessagesHelper.translateColors(advancementMessage);

        // Insert placeholders into the message
        advancementMessageComponent = PlaceholdersHelper.setPlaceholders(advancementMessageComponent, event, plugin);

        // Set the custom message as the system message
        event.message(advancementMessageComponent);
    }
}
