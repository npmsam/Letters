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
    private final Letters plugin;

    public PlayerAdvancementDoneListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerAdvancementDone(@NotNull PlayerAdvancementDoneEvent event) {
        if (event.getAdvancement().getDisplay() == null || event.getAdvancement().getParent() == null)
            return;

        String advancementMessage = MessagesHelper.resolveRandomMessage(event.getPlayer(), "advancement", plugin);

        advancementMessage = PlaceholderApiHook.usePlaceholderAPI(event.getPlayer(), advancementMessage);
        Component advancementMessageComponent = MessagesHelper.translateColors(advancementMessage);
        advancementMessageComponent = PlaceholdersHelper.setPlaceholders(advancementMessageComponent, event, plugin);

        event.message(advancementMessageComponent);
    }
}