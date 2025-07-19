package dev.rohrjaspi.bot;

import dev.rohrjaspi.bot.commands.SetupCommand;
import dev.rohrjaspi.bot.commands.util.CommandManager;
import dev.rohrjaspi.bot.embed.ErrorEmbed;
import dev.rohrjaspi.bot.embed.NewSongEmbed;
import dev.rohrjaspi.util.SettingsLoader;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

@Slf4j
public class BotManager {

    @Getter
    private JDA jda;
    private JDABuilder builder;

    public void init(CommandManager commandManager) {
        try {
            this.builder = JDABuilder.createDefault(System.getenv("DISCORD_TOKEN"));

            builder.enableIntents(
                    GatewayIntent.MESSAGE_CONTENT,
                    GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_MESSAGES
            );

            commandManager.add(new SetupCommand());

            builder.addEventListeners(commandManager); // ✅ correct way
            jda = builder.build();                     // ✅ build AFTER registering listeners
            jda.awaitReady();

            ErrorEmbed errorEmbed = new ErrorEmbed(jda);
            NewSongEmbed newSongEmbed = new NewSongEmbed(jda);

            log.info("DiscordBot has been initialized!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
