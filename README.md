
# Discord Spotify Bot

This is a Discord Bot that access the Spotify API to request the latest Song/Episode of an Artist.


## Environment Variables

To run this project, you will need to set the following environment variables.

`SPOTIFY_CLIENT_SECRET`

`SPOTIFY_CLIENT_ID`

`DISCORD_TOKEN`

To get the SPOTIFY_CLIENT_ID and SPOTIFY_CLIENT_SECRET, you need to create a [Spotify App](https://developer.spotify.com)

To get the DISCORD_TOKEN, you need to create a Discord Bot in the [Discord Developer Dashboard](https://discord.com/developers)
## Installation

First pull the GitHub repo

```bash
  git pull https://github.com/RohrJaspi/Discord-Spotify-Bot.git
  gradle shadowJar
```

Create a docker img (this must be executed in the project directory)

```bash
  docker build -t rohrjaspi/discord-spotify-bot:1.0 .
```

Start a container

```bash
  docker run rohrjaspi/discord-spotify-bot:1.0
```
## Documentation

To setup the Bot run the following command.


```bash
  /setup <channel> <artistID> <title> <role>
```

`channel` Is the channel in witch the message gets send.

`artistID` Is the ID from the Spotify Artist used to access it via the API.

`title` The title that should be sent before the embed.

`role` The role that should get pinged when a message gets send.
