package dev.rohrjaspi.bot.embed;

import dev.rohrjaspi.Save;
import dev.rohrjaspi.SpotifyBot;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

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
        TextChannel channel = api.getTextChannelById(channelID);

        if (channelID == null || channelID.isEmpty()) {
            log.warn("Channel ID is not set. Please set the channel ID in the Save class.");
            return;
        }

        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("Error")
                .setColor(Color.RED)
                .setDescription("Something went wrong! Please try again later.")
                .addField("Error", error.getMessage(), true)
                .addField("Stack Message", error.getMessage(), false)
                .addField("Strack Trace", Arrays.toString(error.getStackTrace()), false);

        assert channel != null;
        channel.sendMessageEmbeds(embed.build()).queue(
            success -> log.info("Error embed sent successfully."),
            failure -> log.error("Failed to send error embed: {}", failure.getMessage())
        );


    }

}
