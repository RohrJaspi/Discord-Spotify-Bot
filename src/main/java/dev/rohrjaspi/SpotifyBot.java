package dev.rohrjaspi;

import dev.rohrjaspi.bot.BotManager;
import dev.rohrjaspi.bot.commands.util.CommandManager;
import dev.rohrjaspi.cronJob.NewTrackCronJob;
import dev.rohrjaspi.cronJob.TokenCronJob;
import dev.rohrjaspi.util.JsonHandler;
import dev.rohrjaspi.util.SettingsLoader;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Slf4j
public class SpotifyBot {

    private static boolean isTrackJobRunning = false;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    @Getter
    public static Save saveInstance;
    @Getter
    public static JsonHandler jsonHandler;

    public static void main(String[] args) {

        jsonHandler = new JsonHandler();
        jsonHandler.readJson("data/data.json", Save.class);
        SettingsLoader settings = new SettingsLoader();

        saveInstance = new Save();

        BotManager manager = new BotManager();
        CommandManager commandManager = new CommandManager();
        manager.init(commandManager);

       TokenCronJob tokenCronJob = new TokenCronJob();

        scheduler.scheduleAtFixedRate(tokenCronJob, 0, 55, TimeUnit.MINUTES);

        if (saveInstance.isSetup) {
            log.info("Bot is already setup, starting New Track Cron Job");
            startNewTrackCronJob();
        } else {
            log.info("Bot is not setup, please run the setup command");
        }
    }


    public static void startNewTrackCronJob() {
        if (isTrackJobRunning) return;

        scheduler.scheduleAtFixedRate(new NewTrackCronJob(), 1, 20, TimeUnit.MINUTES);
        isTrackJobRunning = true;
        log.info("New Track Cron Job has been started");
    }
}
