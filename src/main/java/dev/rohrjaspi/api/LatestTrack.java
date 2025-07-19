package dev.rohrjaspi.api;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import dev.rohrjaspi.Save;
import dev.rohrjaspi.SpotifyBot;
import dev.rohrjaspi.bot.embed.NewSongEmbed;
import dev.rohrjaspi.util.JsonHandler;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Slf4j
public class LatestTrack {

    Save save = SpotifyBot.getSaveInstance();

    private final String artistID = save.getArtistID();
    private final String accessToken = save.getAuthToken();

    public void getLatestTrack() {
        OkHttpClient client = new OkHttpClient();

        log.debug("ArtistID " + save.getArtistID());
        log.debug("AuthToken " + save.getAuthToken());

        HttpUrl url = HttpUrl.parse("https://api.spotify.com/v1/shows/" + artistID + "/episodes")
                .newBuilder()
                .addQueryParameter("limit", "1")
                .addQueryParameter("market", "DE")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .get()
                .build();

        log.debug("Final URL: {}", url);
        log.debug("AccessToken: {}", accessToken);
        log.debug("ArtistID: {}", artistID);


        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    log.info("Successfully fetched latest track.");
                    String body = response.body().string();

                    Episode latest = parseLatestEpisode(body);
                    if (latest != null && isNewTrack(latest.releaseDate)) {
                        if (latest != null && latest.images != null && !latest.images.isEmpty()) {
                            String imageUrl = latest.images.get(latest.images.size() - 1).url; // z.B. 64px
                            log.debug("Selected image: " + imageUrl);
                            NewSongEmbed.sendEmbed(latest.name, imageUrl, latest.externalUrls.spotify);
                            save.setLastReleaseDate(latest.releaseDate);
                            SpotifyBot.getJsonHandler().insertJson("data/data.json", SpotifyBot.getSaveInstance());
                            log.info("Successfully saved data to data.json");
                        }
                    }
                } else {
                    log.error("Failed to fetch latest track: HTTP {} - {}", response.code(), response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                log.error("Failed to fetch latest track.", e);
                e.printStackTrace();
            }
        });
    }

    private boolean isNewTrack(String releaseDate) {
        String lastReleaseDate = save.getLastReleaseDate();
        if (lastReleaseDate == null || !lastReleaseDate.equals(releaseDate)) {
            save.setLastReleaseDate(releaseDate);
            return true;
        }
        return false;
    }

    private Episode parseLatestEpisode(String json) {
        try {
            EpisodesResponse response = new Gson().fromJson(json, EpisodesResponse.class);
            if (response != null && response.items != null && !response.items.isEmpty()) {
                return response.items.get(0);
            }
        } catch (Exception e) {
            log.error(e.getMessage()); // or log it
        }
        return null;
    }

}
