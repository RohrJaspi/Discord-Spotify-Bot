package dev.rohrjaspi.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Episode {
    @SerializedName("name")
    public String name;

    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("external_urls")
    public ExternalUrls externalUrls;

    public List<Image> images;
}
