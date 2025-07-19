FROM eclipse-temurin:21-jre-alpine
LABEL authors="RohrJaspi"

COPY build/libs/DiscordSpotifyBot-1.0-SNAPSHOT.jar /app/DiscordSpotifyBot-1.0-SNAPSHOT.jar

ENTRYPOINT exec java -jar /app/DiscordSpotifyBot-1.0-SNAPSHOT.jar