package com.sampreet.letters.helpers;

import com.sampreet.letters.Letters;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PlaceholdersHelper {
    private PlaceholdersHelper() {
    }

    public static Component setPlaceholders(Component messageComponent, Event event, Letters plugin) {
        if (messageComponent == null)
            return null;

        if (plugin != null) {
            messageComponent = messageComponent.replaceText(builder -> builder.matchLiteral("<plugin_version>")
                    .replacement(Component.text(plugin.getDescription().getVersion())));
        }

        if (event instanceof PlayerJoinEvent playerJoinEvent) {
            messageComponent = messageComponent.replaceText(builder -> builder.matchLiteral("<player>")
                    .replacement(playerComponent(playerJoinEvent.getPlayer())));
        }

        if (event instanceof PlayerQuitEvent playerQuitEvent) {
            messageComponent = messageComponent.replaceText(builder -> builder.matchLiteral("<player>")
                    .replacement(playerComponent(playerQuitEvent.getPlayer())));
        }

        return messageComponent;
    }

    public static @NotNull Component playerComponent(@NotNull Player player) {
        return Component.text(player.getDisplayName())
                .hoverEvent(
                        HoverEvent.showText(
                                Component.text(player.getName())
                                        .appendNewline()
                                        .append(Component.text("Type: Player"))
                                        .appendNewline()
                                        .append(Component.text(player.getUniqueId().toString()))))
                .clickEvent(
                        ClickEvent.suggestCommand("/tell " + player.getName() + " "));
    }
}