package a6;

import java.applet.*;
import java.net.*;

public class LoopSound {
    public static void myLoop(String url) throws InterruptedException
    {

        try
           {
                AudioClip loop = Applet.newAudioClip(new URL(url));
                loop.loop();

            } catch (MalformedURLException murle) {
                System.out.println(murle);
                }
    }
}