package com.sampreet.letters.listeners;

import com.sampreet.letters.Letters;
import com.sampreet.letters.helpers.MessagesHelper;
import com.sampreet.letters.helpers.VanishPlaceholdersHelper;
import com.sampreet.letters.hooks.PlaceholderApiHook;
import de.myzelyam.api.vanish.PlayerHideEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class PlayerHideListener implements Listener {
    private final Letters plugin;

    public PlayerHideListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerHide(@NotNull PlayerHideEvent event) {
        if (event.isSilent())
            return;

        String hideMessage = MessagesHelper.resolveRandomMessage(event.getPlayer(), "quit", plugin);

        hideMessage = PlaceholderApiHook.usePlaceholderAPI(event.getPlayer(), hideMessage);

        Component hideMessageComponent = MessagesHelper.translateColors(hideMessage);

        hideMessageComponent = VanishPlaceholdersHelper.setPlaceholders(hideMessageComponent, event, plugin);

        event.setSilent(true);

        if (hideMessageComponent != null)
            Bukkit.broadcast(hideMessageComponent);
    }
}
