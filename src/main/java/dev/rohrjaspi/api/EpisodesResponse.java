package dev.rohrjaspi.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class EpisodesResponse {
    @SerializedName("items")
    public List<Episode> items;
}

