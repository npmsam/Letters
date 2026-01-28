# Letters [![Latest Release](https://img.shields.io/github/v/release/npmsam/Letters?label=Latest%20Release&style=flat)](https://github.com/npmsam/Letters/releases)

🎉 Letters is a simple and modern Paper plugin for customizing server messages.
Server messages shouldn't be hard to change, they're just [Letters](https://github.com/npmsam/Letters) on the screen anyway 😉.
This plugin makes it easy to control and customize the default messages on your server.

## ✨ Features

The plugin lets you customize messages for the following most common server events.

- Player join and first join
- Player quit
- Player death
- Player chat
- Player whisper (`/tell`, `/msg`, `/w`)
- Player advancements

If you use a vanish plugin like SuperVanish or PremiumVanish,
join and quit messages will hide or show correctly when players vanish or reappear. 

The plugin makes it easy to manage message behavior without using multiple plugins by letting you set different messages for
- Everyone on the server
- Specific [LuckPerms](https://luckperms.net/) groups
- Individual players by username

To make sure you can customize messages however you like, the plugin supports
- Classic Minecraft [`&` color and formatting codes](https://minecraft.wiki/w/Formatting_codes)
- PaperMC [MiniMessage formatting](https://docs.papermc.io/adventure/minimessage/format/)
- Built in placeholders for each event
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) for extra placeholders from other plugins

## 🏷️ Placeholders

The plugin includes basic placeholders for every message type.
These are listed in the default config file.
For more placeholders,
you can install [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) and any expansions you need.

## 🎨 Formatting

You can format messages using [classic Minecraft codes](https://minecraft.wiki/w/Formatting_codes) like `&a` for green text
or `&l` for bold text. You can also use [MiniMessage](https://docs.papermc.io/adventure/minimessage/format/) for cleaner formatting.
For example `<green>` makes text green and `<bold>` makes text bold.

## ⚙️ Configuration

All the messages used by the plugin are stored in and can be customized in `config.yml`.
Messages follow a well-defined priority order which is
- `Player specific > LuckPerms group based > Default messages`

Messages can be disabled by setting them to empty strings (`""`).
The default config includes examples and all built in placeholders,
making it easy to get started.

```yml
default:
  join:
    - "[...]"
  quit:
    - "[...]"

  # Default messages apply to all players unless they are overridden.
  # You can find examples for all supported events in the default config file.

group_based:
  admin:
    join:
      - "[...]"
    quit:
      - "[...]"

  # Add more groups using the same format.
  # Group names must match LuckPerms group names exactly.

player_specific:
  Notch:
    join:
      - "[...]"
    quit:
      - "[...]"

  # Add more players using the same format.
  # Player names must match usernames exactly.
```

## 📦 Download

You can download a ready to use JAR file from the [GitHub releases](https://github.com/npmsam/Letters/releases) page or the [Hangar](https://hangar.papermc.io/Samboii/Letters) page.
This is the easiest option if you do not want to build the plugin yourself.

The default config file includes all supported events and built in placeholders.
Reading it is a simple way to learn how the plugin works and get started quickly.

## 🔧 Building

If you want to build the plugin JAR file yourself, you can use [Apache Maven](https://maven.apache.org/).

Open a terminal or command prompt, go to the plugin folder you cloned or extracted,
and run the following command

```bash
mvn clean package
```

## 🧾 Licensing

This plugin is licensed under the [MIT License](https://en.wikipedia.org/wiki/MIT_License).

You are free to use, copy, modify, merge, publish, distribute, sublicense,
and sell copies of the plugin, as long as the original copyright notice
and this permission notice are included.

See the [LICENSE](https://github.com/npmsam/Letters/blob/main/LICENSE) file for full license details.