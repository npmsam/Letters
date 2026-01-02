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
        if (!event.getPlayer().hasPermission("letters.advancement"))
            return;

        // Return if the advancement has no display
        if (event.getAdvancement().getDisplay() == null)
            return;

        // Disable game-rule so the vanilla message does not show
        disableVanillaAdvancementMessage(event);

        // Try getting the player-specific message from config.yml
        String advancementMessage = plugin.getUtils().getRandomMessage("messages.players." + event.getPlayer().getName() + ".advancement");
        // If none found, fall back to default message
        if (advancementMessage==null) {
            // Retrieve a random message from config.yml
            advancementMessage = plugin.getUtils().getRandomMessage("messages.default.advancement");
        }
        // Insert placeholders and colors into the message
        advancementMessage = plugin.getUtils().setPlaceholders(advancementMessage, event);

        // Broadcast the custom message to the server
        event.getPlayer().getServer().broadcastMessage(advancementMessage);
    }

    // Prime example of "If it works, don't touch it..."
    @SuppressWarnings("unchecked")
    private void disableVanillaAdvancementMessage(PlayerAdvancementDoneEvent event) {
        GameRule<Boolean> advancementRule = null;

        for (String fieldName : new String[] { "ANNOUNCE_ADVANCEMENTS", "SHOW_ADVANCEMENT_MESSAGES" }) {
            try {
                Field field = GameRule.class.getField(fieldName);
                advancementRule = (GameRule<Boolean>) field.get(null);
                break;
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }
        }

        if (advancementRule == null)
            return;

        if (Boolean.TRUE.equals(
                event.getPlayer().getWorld().getGameRuleValue(advancementRule))) {
            event.getPlayer().getWorld().setGameRule(advancementRule, false);
        }
    }
}
