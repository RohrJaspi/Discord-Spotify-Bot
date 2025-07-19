package dev.rohrjaspi.cronJob;

import dev.rohrjaspi.api.LatestTrack;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NewTrackCronJob implements Runnable {

    @Override
    public void run() {

        LatestTrack latestTrack = new LatestTrack();
        latestTrack.getLatestTrack();
        log.info("NewTrackCronJob started.");

    }
}
