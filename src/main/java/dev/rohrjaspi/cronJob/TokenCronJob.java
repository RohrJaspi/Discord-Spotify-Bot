package dev.rohrjaspi.cronJob;

import dev.rohrjaspi.Save;
import dev.rohrjaspi.SpotifyBot;
import dev.rohrjaspi.api.AuthTokenManager;
import dev.rohrjaspi.api.TokenCallback;
import dev.rohrjaspi.bot.embed.ErrorEmbed;
import dev.rohrjaspi.util.SettingsLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenCronJob implements Runnable {

    private final String clientID = System.getenv("SPOTIFY_CLIENT_ID");
    private final String clientSecret = System.getenv("SPOTIFY_CLIENT_SECRET");

    @Override
    public void run() {
        AuthTokenManager tokenManager = new AuthTokenManager(clientID, clientSecret);

        tokenManager.generateToken(new TokenCallback() {
            @Override
            public void onTokenReceived(String token) {
                Save save = SpotifyBot.getSaveInstance();
                save.setAuthToken(token);
                log.info("Token has successfully been generated and saved!");
                log.info("TokenCronJob started. With new token: {}", token);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("Token generation failed!", throwable);
                ErrorEmbed.sendErrorEmbed(new Error(throwable));
            }
        });
    }
}
