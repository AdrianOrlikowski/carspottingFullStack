package pl.orlikowski.carspottingBack.globals;

import pl.orlikowski.carspottingBack.security.SecType;

public abstract class Globals {
    public static final String picPath = "http://127.0.0.1:8887/";
    public static final String savePath = "//Users/adrianorlikowski/Java_Projekty/images/";
    public static final SecType secType = SecType.HTTP_BASIC;
    public static final int tokenSize = 10;
    public static final int resetPassLength = 10;
    public static final String mailFrom = "carspotting@warsaw.pl";
    public static final String activationLink ="localhost:8080/users/activate?token=";
}
