package com.sampreet.letters.helpers;

import com.sampreet.letters.Letters;
import io.papermc.paper.advancement.AdvancementDisplay;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.advancement.Advancement;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceholdersHelper {
    private PlaceholdersHelper() {
    }

    public static Component setPlaceholders(
            Component messageComponent,
            CommandSender sender,
            Player recipient,
            String whisperMessage,
            Letters plugin) {
        if (messageComponent == null) return null;

        if (sender instanceof Player player) {
            messageComponent = replaceLiteral(
                    messageComponent,
                    "<sender>",
                    playerComponent(player)
            );
        }

        if (recipient != null) {
            messageComponent = replaceLiteral(
                    messageComponent,
                    "<recipient>",
                    playerComponent(recipient)
            );
        }

        if (whisperMessage != null) {
            messageComponent = replaceLiteral(
                    messageComponent,
                    "<message>",
                    Component.text(whisperMessage)
            );
        }

        return setPlaceholders(messageComponent, null, plugin);
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

        if (event instanceof PlayerDeathEvent playerDeathEvent) {
            messageComponent = replaceLiteral(
                    messageComponent,
                    "<message>",
                    playerDeathEvent.deathMessage()
            );
        }

        if (event instanceof AsyncChatEvent asyncChatEvent) {
            messageComponent = replaceLiteral(
                    messageComponent,
                    "<message>",
                    asyncChatEvent.message()
            );
        }

        if (event instanceof PlayerAdvancementDoneEvent playerAdvancementDoneEvent) {
            messageComponent = replaceLiteral(
                    messageComponent,
                    "<advancement>",
                    advancementComponent(playerAdvancementDoneEvent.getAdvancement())
            );

            if (playerAdvancementDoneEvent.getAdvancement().getDisplay() != null)
                messageComponent = replaceLiteral(
                        messageComponent,
                        "<color>",
                        frameColorComponent(playerAdvancementDoneEvent.getAdvancement().getDisplay().frame())
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
        if (event instanceof PlayerJoinEvent playerJoinEvent)
            return playerJoinEvent.getPlayer();
        if (event instanceof PlayerQuitEvent playerQuitEvent)
            return playerQuitEvent.getPlayer();
        if (event instanceof PlayerDeathEvent playerDeathEvent)
            return playerDeathEvent.getPlayer();
        if (event instanceof AsyncChatEvent asyncChatEvent)
            return asyncChatEvent.getPlayer();
        if (event instanceof PlayerAdvancementDoneEvent playerAdvancementDoneEvent)
            return playerAdvancementDoneEvent.getPlayer();
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

    public static @Nullable Component advancementComponent(@NotNull Advancement advancement) {
        AdvancementDisplay advancementDisplay = advancement.getDisplay();

        if (advancementDisplay == null)
            return null;

        NamedTextColor advancementColor = frameColor(advancementDisplay.frame());
        Component advancementTitle = advancementDisplay.title().color(advancementColor);

        Component hover = advancementDisplay.title().color(advancementColor)
                .appendNewline()
                .append(advancementDisplay.description().color(advancementColor));

        return advancementTitle.hoverEvent(HoverEvent.showText(hover));
    }

    private static @NotNull Component frameColorComponent(@NotNull AdvancementDisplay.Frame frame) {
        return Component.empty().color(frameColor(frame));
    }

    private static NamedTextColor frameColor(@NotNull AdvancementDisplay.Frame frame) {
        return switch (frame) {
            case TASK -> NamedTextColor.GREEN;
            case GOAL -> NamedTextColor.LIGHT_PURPLE;
            case CHALLENGE -> NamedTextColor.GOLD;
        };
    }
}