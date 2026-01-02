package com.sampreet.letters.listeners;

import com.sampreet.letters.Letters;
import org.bukkit.GameRule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.Field;

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

        // Retrieve a random message from config.yml
        String advancementMessage = plugin.getUtils().resolveRandomMessage(event.getPlayer(), "advancement");
        // Insert placeholders and colors into the message
        advancementMessage = plugin.getUtils().setPlaceholders(advancementMessage, event);

        // Broadcast the custom message to the server
        event.getPlayer().getServer().broadcastMessage(advancementMessage);
    }

    // Prime example of "If it works, don't touch it..."
    @SuppressWarnings("unchecked")
    private void disableVanillaAdvancementMessage(PlayerAdvancementDoneEvent event) {
        GameRule<Boolean> advancementRule = null;

        for (String fieldName : new String[]{"ANNOUNCE_ADVANCEMENTS", "SHOW_ADVANCEMENT_MESSAGES"}) {
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
