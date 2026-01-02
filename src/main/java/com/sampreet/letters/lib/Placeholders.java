package com.sampreet.letters.lib;

// Enum to store centralized placeholders definitions
public enum Placeholders {
    // Player name placeholders
    PLAYER_NAME("%player_name%"),
    SENDER_NAME("%sender_name%"),
    RECIPIENT_NAME("%recipient_name%"),
    // Messages placeholders
    CHAT_MESSAGE("%chat_message%"),
    DEATH_MESSAGE("%death_message%"),
    WHISPER_MESSAGE("%whisper_message%"),
    // Advancement placeholders
    ADVANCEMENT_NAME("%advancement_name%"),
    ADVANCEMENT_COLOR("%advancement_color%"),
    // Plugin placeholders
    PLUGIN_VERSION("%plugin_version%");

    private final String key;

    Placeholders(String key) {
        this.key = key;
    }

    // Function to retrieve placeholder
    public String key() {
        return key;
    }
}
