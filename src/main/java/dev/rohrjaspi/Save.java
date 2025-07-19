package dev.rohrjaspi;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Save {


    public String authToken = "";
    public String channelID = "";
    public String lastReleaseDate = "";
    public String artistID = "";
    public String title = "";
    public String rolePingID = "";
    public Boolean isSetup = false;
}
