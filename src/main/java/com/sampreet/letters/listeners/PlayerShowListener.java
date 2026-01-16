package com.sampreet.letters.listeners;

import com.sampreet.letters.Letters;
import com.sampreet.letters.helpers.MessagesHelper;
import com.sampreet.letters.helpers.PlaceholdersHelper;
import com.sampreet.letters.hooks.PlaceholderApiHook;
import de.myzelyam.api.vanish.PlayerShowEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerShowListener implements Listener {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public PlayerShowListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerShow(PlayerShowEvent event) {
        // Return if the event is silent
        if (event.isSilent()) return;

        // Retrieve a random message from config.yml
        String showMessage = MessagesHelper.resolveRandomMessage(event.getPlayer(), "join", plugin);

        // Insert PlaceholderAPI placeholders into the message
        showMessage = PlaceholderApiHook.usePlaceholderAPI(event.getPlayer(), showMessage);

        // Put colors into the message by translating & color codes and mini message
        Component showMessageComponent = MessagesHelper.translateColors(showMessage);

        // Insert placeholders into the message
        showMessageComponent = PlaceholdersHelper.setPlaceholders(showMessageComponent, event, plugin);

        // Prevent the vanish plugin from sending a join message
        event.setSilent(true);

        // Set the custom message as the system message
        if (showMessageComponent != null)
            Bukkit.broadcast(showMessageComponent);
    }
}
