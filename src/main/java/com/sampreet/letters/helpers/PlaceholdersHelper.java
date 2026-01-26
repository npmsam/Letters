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
            messageComponent = replaceLiteral(
                    messageComponent,
                    "<plugin_version>",
                    Component.text(plugin.getDescription().getVersion())
            );
        }

        Player player = extractPlayer(event);
        if (player != null) {
            messageComponent = replaceLiteral(
                    messageComponent,
                    "<player>",
                    playerComponent(player)
            );
        }

        return messageComponent;
    }

    public static Component setPlaceholders(Component messageComponent, Letters plugin) {
        return setPlaceholders(messageComponent, null, plugin);
    }

    public static Component replaceLiteral(Component component, String key, Component value) {
        return component.replaceText(builder ->
                builder.matchLiteral(key).replacement(value));
    }

    private static Player extractPlayer(Event event) {
        if (event instanceof PlayerJoinEvent join) {
            return join.getPlayer();
        }
        if (event instanceof PlayerQuitEvent quit) {
            return quit.getPlayer();
        }
        return null;
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