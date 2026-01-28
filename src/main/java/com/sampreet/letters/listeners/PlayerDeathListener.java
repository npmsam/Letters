package com.sampreet.letters.listeners;

import com.sampreet.letters.Letters;
import com.sampreet.letters.helpers.MessagesHelper;
import com.sampreet.letters.helpers.PlaceholdersHelper;
import com.sampreet.letters.hooks.PlaceholderApiHook;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private final Letters plugin;

    public PlayerDeathListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent playerDeathEvent) {
        String deathMessage = MessagesHelper.resolveRandomMessage(playerDeathEvent.getPlayer(), "death", plugin);

        deathMessage = PlaceholderApiHook.usePlaceholderAPI(playerDeathEvent.getPlayer(), deathMessage);
        Component deathMessageComponent = MessagesHelper.translateColors(deathMessage);
        deathMessageComponent = PlaceholdersHelper.setPlaceholders(deathMessageComponent, playerDeathEvent, plugin);

        playerDeathEvent.deathMessage(deathMessageComponent);
    }
}