# Letters [![Latest Release](https://img.shields.io/github/v/release/npmsam/Letters?label=Latest%20Release&style=flat)](https://github.com/npmsam/Letters/releases)

Customizing server messages in Minecraft shouldn't be so hard, they're just [Letters](https://github.com/npmsam/Letters)
on the screen anyway! That's why I made this plugin, to make them easy to customize.

## Features

The plugin currently supports custom server messages for the following events. These are the most commonly used events
on a typical server. More events may be added later, but there are no plans for now.

- Player join
- Player quit
- Player death
- Chat messages
- Player advancement
- Whisper / private messages

If a vanish plugin (SuperVanish or PremiumVanish) is installed, join and quit messages are suppressed or shown
appropriately when a player vanishes or reappears, ensuring messages reflect their visible status.

The plugin lets you define custom server messages not only for everyone on the server but also for specific permission
groups defined in [LuckPerms](https://luckperms.net/) and for specific players by matching their username.

Messages can be customized using Minecraft color codes or PaperMC’s MiniMessage format. Built-in placeholders are
included for each event and [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) can be used for
additional placeholders from other plugins.

## Placeholders

The plugin provides a small set of built-in placeholders for each message type to handle basic functionality, which vary
depending on the specific event and can be found in the default configuration file. For more placeholders, you can
install [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) along with any expansion packs to use
placeholders from other plugins on your server.

## Formatting

Messages can be customized using Minecraft legacy color and formatting codes. Legacy codes use the `&` symbol, for
example `&a` for green text or `&l` for bold. You can find a full list of supported codes in
the [Minecraft formatting codes documentation](https://minecraft.wiki/w/Formatting_codes).

Messages can also be customized using MiniMessage formatting, which provides a simpler and more readable way to style
text. For example, `<green>` makes text green and `<bold>` makes it bold. See
the [PaperMC MiniMessage documentation](https://docs.papermc.io/adventure/minimessage/format/) for more details and
examples.

Mixing both formats is supported. You can use the old `&` codes with the more readable MiniMessage tags, though it’s
recommended to avoid mixing them as it can make messages harder to read and manage. For example, `&a<bold>Hello</bold>`
will show “Hello” in green and bold.

## Config Structure

The `messages` section contains all server messages handled by the plugin. Custom messages can be set for specific
permission groups under `groups`, using group names from [LuckPerms](https://luckperms.net/). Messages can also be set
for specific players under `players`, where the player’s username is matched exactly. The message format is the same in
all sections.

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

You can download a prebuilt JAR file from the [GitHub releases](https://github.com/npmsam/Letters/releases) page if
you
don't want to build the plugin yourself. The default config file lists all supported events and built-in placeholders,
so reading it is a quick way to understand how the plugin works and get started.

## Building

You can use [Apache Maven](https://maven.apache.org/) to build the plugin yourself if you don't want to download a
prebuilt JAR file. Open your terminal or command prompt and navigate to the plugin directory (cloned or extracted), then
run the following command:

```bash
mvn clean package
```

## Licensing

This project is licensed under the [MIT License](https://en.wikipedia.org/wiki/MIT_License). You are free to use, copy,
modify, merge, publish, distribute, sublicense, and/or sell copies if the original copyright notice and this permission
notice are included. Read the [LICENSE](https://github.com/npmsam/Letters/blob/main/LICENSE) file for more
information.
