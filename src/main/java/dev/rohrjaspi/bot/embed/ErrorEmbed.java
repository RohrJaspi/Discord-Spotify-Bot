package dev.rohrjaspi.bot.embed;

import dev.rohrjaspi.Save;
import dev.rohrjaspi.SpotifyBot;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;

import java.awt.*;
import java.util.Arrays;

@Slf4j
public class ErrorEmbed {

    private static JDA api;

    public ErrorEmbed(JDA api) {
        this.api = api;
    }

    public static void sendErrorEmbed(Error error) {

        Save save = SpotifyBot.getSaveInstance();
        String channelID = save.getChannelID();

        GuildChannel rawChannel;
        try {
            rawChannel = api.getGuildChannelById(Long.parseLong(channelID));
        } catch (NumberFormatException e) {
            log.error("Invalid channel ID: " + channelID, e);
            return;
        }

        if (!(rawChannel instanceof GuildMessageChannel channel)) {
            log.error("Channel is not a message-capable channel: " + channelID);
            return;
        }

        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("Error")
                .setColor(Color.RED)
                .setDescription("Something went wrong! Please try again later.")
                .addField("Error", error.getMessage(), true)
                .addField("Stack Message", error.getMessage(), false)
                .addField("Strack Trace", Arrays.toString(error.getStackTrace()), false);


        channel.sendMessageEmbeds(embed.build()).queue(
            success -> log.info("Error embed sent successfully."),
            failure -> log.error("Failed to send error embed: {}", failure.getMessage())
        );


    }

}
