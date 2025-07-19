package dev.rohrjaspi.api;

public interface TokenCallback {
    void onTokenReceived(String token);
    void onError(Throwable throwable);
}
