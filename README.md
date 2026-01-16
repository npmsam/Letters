# Letters [![Latest Release](https://img.shields.io/github/v/release/npmsam/Letters?label=Latest%20Release&style=flat)](https://github.com/npmsam/Letters/releases)

Customizing server messages in Minecraft should not be difficult. They are
just [Letters](https://github.com/npmsam/Letters) on the screen.  
That is why I made Letters, to make server messages easy to change and manage.

## Features

The plugin supports custom messages for the most common server events:

- Player join
- Player quit
- Player death
- Chat messages
- Player advancements
- Whisper or private messages

More events may be added in the future, but there are no plans for that right now.

If you use a vanish plugin like SuperVanish or PremiumVanish, join and quit messages will be hidden or shown correctly
when a player vanishes or becomes visible again.

You can set different messages for:

- Everyone on the server
- Specific permission groups using [LuckPerms](https://luckperms.net/)
- Specific players by username

Messages support Minecraft color codes or PaperMC MiniMessage formatting. Each event includes built-in placeholders, and
you can use [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) to add placeholders from other
plugins.

## Placeholders

Letters includes a small set of built-in placeholders for each message type. These cover the most basic needs and are
listed in the default configuration file.

For more advanced placeholders, you can
install [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) and any expansion packs you need.

## Formatting

Messages can use classic Minecraft formatting codes with the & symbol, such as &a for green text or &l for bold.
A full list of codes is available in the
[Minecraft formatting codes documentation](https://minecraft.wiki/w/Formatting_codes).

You can also use MiniMessage, which is cleaner and easier to read. For example, <green> makes text green and
<bold> makes text bold. See the
[PaperMC MiniMessage documentation](https://docs.papermc.io/adventure/minimessage/format/).

## Config Structure

The `messages` section contains all server messages handled by the plugin. You can set custom messages for specific
permission groups under `groups`, using group names from [LuckPerms](https://luckperms.net/). Messages can also be set
for specific players under
`players`, where the player username must match exactly. The message format is the same in all sections.

```yml
messages:
  default:
    join:
      - "[...]"
    quit:
      - "[...]"

    # Default messages apply to all players unless they are overridden.
    # You can find examples for all supported events in the default config file.

  groups:
    admin:
      join:
        - "[...]"
      quit:
        - "[...]"

    # Add more groups using the same format.
    # Group names must match LuckPerms group names exactly.

  players:
    Notch:
      join:
        - "[...]"
      quit:
        - "[...]"

    # Add more players using the same format.
    # Player names must match usernames exactly.
```

## Download

You can download a prebuilt JAR file from the [GitHub releases](https://github.com/npmsam/Letters/releases) page if you
do not want to build the plugin yourself.
The default configuration file lists all supported events and built-in placeholders, so reading it is an easy way to
understand how the plugin works and get started.

## Building

If you prefer to build the plugin yourself, you can use [Apache Maven](https://maven.apache.org/). Open a terminal or
command prompt, go to the
plugin directory that you cloned or extracted, and run this command:

```bash
mvn clean package
```

## Licensing

This project is licensed under the [MIT License](https://en.wikipedia.org/wiki/MIT_License). You are free to use, copy,
modify, merge, publish, distribute,
sublicense, and sell copies of the plugin, as long as the original copyright notice and this permission notice are
included. See the [LICENSE](https://github.com/npmsam/Letters/blob/main/LICENSE) file for more information.
