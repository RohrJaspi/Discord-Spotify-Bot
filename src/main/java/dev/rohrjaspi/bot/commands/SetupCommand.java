package dev.rohrjaspi.bot.commands;

import dev.rohrjaspi.Save;
import dev.rohrjaspi.SpotifyBot;
import dev.rohrjaspi.bot.commands.util.SlashCommand;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

@Slf4j
public class SetupCommand extends SlashCommand {
    @Override
    public String getName() {
        return "setup";
    }

    @Override
    public String getDescription() {
        return "This will setup the bot for you.";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.CHANNEL, "channel", "This is the channel where the bot will send messages.", true), //ChannelID
                new OptionData(OptionType.STRING, "artist", "This is the artist you want to track.", true), //ArtistID
                new OptionData(OptionType.STRING, "title", "This is the title of the message that will be sent.", true),//Title
                new OptionData(OptionType.ROLE, "role", "This is the role that will be pinged when a new track is released.", true) //RoleID
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
            if (!(event.getName().equals("setup"))) return;

            String channelId = event.getOption("channel").getAsChannel().getId();
            String artistId = event.getOption("artist").getAsString();
            String title = event.getOption("title").getAsString();
            String roleId = event.getOption("role") != null ? event.getOption("role").getAsRole().getId() : "none";

            // Save the settings to the Save.class

        Save save = SpotifyBot.getSaveInstance();
        save.setChannelID(channelId);
        save.setArtistID(artistId);
        save.setTitle(title);
        save.setRolePingID(roleId);

        SpotifyBot.startNewTrackCronJob();
        save.setIsSetup(true);
        log.info("isSetup is set to true in Save.class");
        event.reply("Setup completed successfully!").queue();

    }
}
