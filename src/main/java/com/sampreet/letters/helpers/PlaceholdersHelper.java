package com.sampreet.letters.helpers;

import com.sampreet.letters.Letters;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlaceholdersHelper {
    // Prevent instantiation of the class
    private PlaceholdersHelper() {
    }

    // Helper function to insert placeholders into messages
    public static Component setPlaceholders(Component messageComponent, Event event, Letters plugin) {
        // Return null if the message passed is null
        if (messageComponent == null) return null;

        // Use replaceText with match literal to put placeholders in
        if (plugin != null) {
            messageComponent = messageComponent.replaceText(builder ->
                    builder.matchLiteral("<plugin_version>")
                            .replacement(Component.text(plugin.getDescription().getVersion()))
            );
        }

        // Replace placeholders for when player joins server
        if (event instanceof PlayerJoinEvent playerJoinEvent) {
            messageComponent = messageComponent.replaceText(builder ->
                    builder.matchLiteral("<player>")
                            .replacement(playerComponent(playerJoinEvent.getPlayer()))
            );
        }

        // Replace placeholders for when player quits server
        if (event instanceof PlayerQuitEvent playerQuitEvent) {
            messageComponent = messageComponent.replaceText(builder ->
                    builder.matchLiteral("<player>")
                            .replacement(playerComponent(playerQuitEvent.getPlayer()))
            );
        }

        // Return the component with the placeholders set
        return messageComponent;
    }

    // Helper function to use the function above without needing to pass an event
    public static Component setPlaceholders(Component messageComponent, Letters plugin) {
        // Pass null for the event so event
        return setPlaceholders(messageComponent, null, plugin);
    }

    // Creates a vanilla-like player name component.
    public static Component playerComponent(Player player) {
        return Component.text(player.getDisplayName())
                .hoverEvent(
                        HoverEvent.showText(
                                Component.text(player.getName())
                                        .appendNewline()
                                        .append(Component.text("Type: Player"))
                                        .appendNewline()
                                        .append(Component.text("UUID: " + player.getUniqueId()))
                        )
                )
                .clickEvent(
                        ClickEvent.suggestCommand("/tell " + player.getName() + " ")
                );
    }
}
