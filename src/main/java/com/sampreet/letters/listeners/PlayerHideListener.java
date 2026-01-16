package com.sampreet.letters.listeners;

import com.sampreet.letters.Letters;
import com.sampreet.letters.helpers.MessagesHelper;
import com.sampreet.letters.helpers.PlaceholdersHelper;
import com.sampreet.letters.hooks.PlaceholderApiHook;
import de.myzelyam.api.vanish.PlayerHideEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerHideListener implements Listener {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public PlayerHideListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerHide(PlayerHideEvent event) {
        // Return if the event is silent
        if (event.isSilent()) return;

        // Retrieve a random message from config.yml
        String hideMessage = MessagesHelper.getRandomMessage("messages.default.quit", plugin);

        // Insert PlaceholderAPI placeholders into the message
        hideMessage = PlaceholderApiHook.usePlaceholderAPI(event.getPlayer(), hideMessage);

        // Put colors into the message by translating & color codes and mini message
        Component hideMessageComponent = MessagesHelper.translateColors(hideMessage);

        // Insert placeholders into the message
        hideMessageComponent = PlaceholdersHelper.setPlaceholders(hideMessageComponent, event, plugin);

        // Prevent the vanish plugin from sending a join message
        event.setSilent(true);

        // Set the custom message as the system message
        if (hideMessageComponent != null)
            Bukkit.broadcast(hideMessageComponent);
    }
}
