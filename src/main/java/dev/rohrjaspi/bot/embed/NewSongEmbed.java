package dev.rohrjaspi.bot.embed;

import dev.rohrjaspi.Save;
import dev.rohrjaspi.SpotifyBot;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

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
        TextChannel channel = jda.getTextChannelById(channelID);

        if (channel == null || channelID == null) {
            log.error(channel + " not found");
            log.error(channelID + " not found");
        }

       channel.sendMessage(save.getTitle() + " <@&" + pingRoleID + ">").setEmbeds(eb.build()).queue();


    }
}
