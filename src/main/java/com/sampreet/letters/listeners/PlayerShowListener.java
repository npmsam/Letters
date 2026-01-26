package com.sampreet.letters.listeners;

import com.sampreet.letters.Letters;
import com.sampreet.letters.helpers.MessagesHelper;
import com.sampreet.letters.helpers.VanishPlaceholdersHelper;
import com.sampreet.letters.hooks.PlaceholderApiHook;
import de.myzelyam.api.vanish.PlayerShowEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class PlayerShowListener implements Listener {
    private final Letters plugin;

    public PlayerShowListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerShow(@NotNull PlayerShowEvent event) {
        if (event.isSilent())
            return;

        String showMessage = MessagesHelper.resolveRandomMessage(event.getPlayer(), "join", plugin);

        showMessage = PlaceholderApiHook.usePlaceholderAPI(event.getPlayer(), showMessage);

        Component showMessageComponent = MessagesHelper.translateColors(showMessage);

        showMessageComponent = VanishPlaceholdersHelper.setPlaceholders(showMessageComponent, event, plugin);

        event.setSilent(true);

        if (showMessageComponent != null)
            Bukkit.broadcast(showMessageComponent);
    }
}
