package com.sampreet.letters.helpers;

import com.sampreet.letters.Letters;
import io.papermc.paper.advancement.AdvancementDisplay;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public final class PlaceholdersHelper {
    // Prevent instantiation of the class
    private PlaceholdersHelper() {
    }

    // Helper function to insert placeholders into messages
    public static Component setPlaceholders(Component messageComponent, Event event, Letters plugin) {
        // Return null if the message passed is null
        if (messageComponent == null)
            return null;

        // Use replaceText with match literal to put placeholders in
        if (plugin != null) {
            messageComponent = messageComponent.replaceText(builder -> builder.matchLiteral("<plugin_version>")
                    .replacement(Component.text(plugin.getDescription().getVersion())));
        }

        // Replace placeholders for when player joins server
        if (event instanceof PlayerJoinEvent playerJoinEvent) {
            messageComponent = messageComponent.replaceText(builder -> builder.matchLiteral("<player>")
                    .replacement(playerComponent(playerJoinEvent.getPlayer())));
        }

        // Replace placeholders for when player quits server
        if (event instanceof PlayerQuitEvent playerQuitEvent) {
            messageComponent = messageComponent.replaceText(builder -> builder.matchLiteral("<player>")
                    .replacement(playerComponent(playerQuitEvent.getPlayer())));
        }

        // Replace placeholders for when player earns an advancement
        if (event instanceof PlayerAdvancementDoneEvent playerAdvancementDoneEvent) {
            messageComponent = messageComponent
                    .replaceText(builder -> builder.matchLiteral("<advancement>")
                            .replacement(advancementComponent(playerAdvancementDoneEvent.getAdvancement())))
                    .replaceText(builder -> builder.matchLiteral("<player>")
                            .replacement(playerComponent(playerAdvancementDoneEvent.getPlayer())));
        }

        // Replace placeholders for when player dies
        if (event instanceof PlayerDeathEvent playerDeathEvent) {
            messageComponent = messageComponent
                    .replaceText(builder -> builder.matchLiteral("<death_message>")
                            .replacement(playerDeathEvent.deathMessage()))
                    .replaceText(builder -> builder.matchLiteral("<player>")
                            .replacement(playerComponent(playerDeathEvent.getPlayer())));
        }

        // Replace placeholders for when player sends a message in server chat
        if (event instanceof AsyncChatEvent asyncChatEvent) {
            messageComponent = messageComponent
                    .replaceText(builder -> builder.matchLiteral("<message>")
                            .replacement(asyncChatEvent.message()))
                    .replaceText(builder -> builder.matchLiteral("<player>")
                            .replacement(playerComponent(asyncChatEvent.getPlayer())));
        }

        // Return the component with the placeholders set
        return messageComponent;
    }

    // Helper function to use the function above without needing to pass an event
    public static Component setPlaceholders(Component messageComponent, Letters plugin) {
        // Pass null for the event so event
        return setPlaceholders(messageComponent, null, plugin);
    }

    // Create a vanilla-like player name component
    public static @NotNull Component playerComponent(@NotNull Player player) {
        return Component.text(player.getDisplayName())
                .hoverEvent(
                        HoverEvent.showText(
                                Component.text(player.getName())
                                        .appendNewline()
                                        .append(Component.text("Type: Player"))
                                        .appendNewline()
                                        .append(Component.text("UUID: " + player.getUniqueId()))))
                .clickEvent(
                        ClickEvent.suggestCommand("/tell " + player.getName() + " "));
    }

    // Create a vanilla-like advancement name component
    public static Component advancementComponent(@NotNull Advancement advancement) {
        // Store the display in a variable
        AdvancementDisplay advancementDisplay = advancement.getDisplay();

        // Return null if the advancement has no display
        if (advancementDisplay == null)
            return null;

        // Store the color of the advancement
        NamedTextColor advancementColor = frameColor(advancementDisplay.frame());

        // Build the title text shown in chat
        Component advancementTitle = advancementDisplay.title().color(advancementColor);

        // Build the tooltip text that shows on hover
        Component hover = advancementDisplay.title().color(advancementColor)
                .appendNewline()
                .append(advancementDisplay.description().color(advancementColor));

        // Return the built component
        return advancementTitle.hoverEvent(HoverEvent.showText(hover));
    }

    // Helper function to get the color of the advancement
    private static NamedTextColor frameColor(@NotNull AdvancementDisplay.Frame frame) {
        return switch (frame) {
            case TASK -> NamedTextColor.GREEN;
            case GOAL -> NamedTextColor.LIGHT_PURPLE;
            case CHALLENGE -> NamedTextColor.GOLD;
        };
    }
}
