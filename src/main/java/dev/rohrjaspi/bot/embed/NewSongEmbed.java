package dev.rohrjaspi.bot.embed;

import dev.rohrjaspi.Save;
import dev.rohrjaspi.SpotifyBot;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class NewSongEmbed {

    static Save save = SpotifyBot.getSaveInstance();

    private static JDA jda;

    public NewSongEmbed(JDA jda) {
        this.jda = jda;
    }

    public static void sendEmbed(String message, String imgUrl, String SongUrl) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm:ss");
        LocalDateTime now = LocalDateTime.now(); // System-Zeit ohne Zeitzone
        String formattedDate = dtf.format(now);

        //sendMessage();

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle(message);
        eb.setThumbnail(imgUrl);
        eb.setColor(0x1DB954); // Spotify green color
        eb.addField("Song Url: ", SongUrl, false);
        eb.setFooter("by RohrJaspi • " + formattedDate);

        String pingRoleID = save.getRolePingID();
        String channelID = save.getChannelID();

        GuildChannel rawChannel;
        try {
            rawChannel = jda.getGuildChannelById(Long.parseLong(channelID));
        } catch (NumberFormatException e) {
            log.error("Invalid channel ID: " + channelID, e);
            return;
        }

        if (!(rawChannel instanceof GuildMessageChannel channel)) {
            log.error("Channel is not a message-capable channel: " + channelID);
            return;
        }

       channel.sendMessage(save.getTitle() + " <@&" + pingRoleID + ">").setEmbeds(eb.build()).queue();


    }
}
