package com.sampreet.letters.listeners;

import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.bukkit.event.EventHandler;
import com.sampreet.letters.Letters;
import org.bukkit.event.Listener;
import java.lang.reflect.Field;
import org.bukkit.GameRule;

public class PlayerAdvancementDoneListener implements Listener {
    // Store plugin instance for accessing config
    private final Letters plugin;

    public PlayerAdvancementDoneListener(Letters plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerAdvancementDone(@NonNull PlayerAdvancementDoneEvent event) {
        // Check if the player has the permission to have custom advancement messages
        if (!event.getPlayer().hasPermission("letters.advancement")) return;

        // Return if the advancement has no display
        if (event.getAdvancement().getDisplay() == null) return;

        // Disable game-rule so the vanilla message does not show
        GameRule<Boolean> showAdvancementMessages = getAdvancementGameRule();
        if (showAdvancementMessages != null && Boolean.TRUE.equals(event.getPlayer().getWorld().getGameRuleValue(showAdvancementMessages))) {
            event.getPlayer().getWorld().setGameRule(showAdvancementMessages, false);
        }

        // Retrieve a random message from config.yml
        String advancementMessage = plugin.getUtils().getRandomMessage("messages.default.advancement");
        // Insert placeholders and colors into the message
        advancementMessage = plugin.getUtils().setPlaceholders(advancementMessage, event);

        // Broadcast the custom message to the server
        event.getPlayer().getServer().broadcastMessage(advancementMessage);
    }

    @SuppressWarnings("unchecked")
    private GameRule<Boolean> getAdvancementGameRule() {
        try {
            Field oldRule = GameRule.class.getField("ANNOUNCE_ADVANCEMENTS");
            return (GameRule<Boolean>) oldRule.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e1) {
            try {
                //noinspection JavaReflectionMemberAccess
                Field newRule = GameRule.class.getField("SHOW_ADVANCEMENT_MESSAGES");
                return (GameRule<Boolean>) newRule.get(null);
            } catch (NoSuchFieldException | IllegalAccessException e2) {
                return null;
            }
        }
    }
}
