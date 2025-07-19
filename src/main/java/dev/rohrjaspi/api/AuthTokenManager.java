package dev.rohrjaspi.api;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Slf4j
public class AuthTokenManager {

    private final String clientId;
    private final String clientSecret;

    public AuthTokenManager(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public void generateToken(TokenCallback callback) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("client_id", clientId)
                .add("client_secret", clientSecret)
                .build();

        Request request = new Request.Builder()
                .url("https://accounts.spotify.com/api/token")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String body = response.body().string();


                    String token = deserializeToken(body);
                    log.debug("Token: " + token);
                    callback.onTokenReceived(token);
                } else {
                    callback.onError(new IOException("HTTP " + response.code() + ": " + response.message()));
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onError(e);
            }
        });
    }

    private String deserializeToken(String json) {

        try {
            TokenResponse response = new Gson().fromJson(json, TokenResponse.class);
            return response != null ? response.accessToken : null;
        } catch (Exception e) {
            e.printStackTrace(); // or use a logger
            return null;
        }
    }
}
