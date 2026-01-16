package com.sampreet.letters.helpers;

import com.sampreet.letters.Letters;
import de.myzelyam.api.vanish.PlayerHideEvent;
import de.myzelyam.api.vanish.PlayerShowEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.event.Event;

public class VanishPlaceholdersHelper {
    // Prevent instantiation of the class
    private VanishPlaceholdersHelper() {
    }

    // Helper function to insert placeholders into messages
    public static Component setPlaceholders(Component messageComponent, Event event, Letters plugin) {
        // Return null if the message passed is null
        if (messageComponent == null)
            return null;

        // Replace placeholders for when player turns off vanish
        if (event instanceof PlayerShowEvent playerShowEvent) {
            messageComponent = messageComponent.replaceText(builder -> builder.matchLiteral("<player>")
                    .replacement(PlaceholdersHelper.playerComponent(playerShowEvent.getPlayer())));
        }

        // Replace placeholders for when player turns on vanish
        if (event instanceof PlayerHideEvent playerHideEvent) {
            messageComponent = messageComponent.replaceText(builder -> builder.matchLiteral("<player>")
                    .replacement(PlaceholdersHelper.playerComponent(playerHideEvent.getPlayer())));
        }

        // Run the original setPlaceholders function to apply default placeholders
        return PlaceholdersHelper.setPlaceholders(messageComponent, plugin);
    }
}
