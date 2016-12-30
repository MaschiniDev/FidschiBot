import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.*;
import org.json.simple.*;

public class main {
    public static void main(String[] args) throws Exception {
        //#buffreader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        /*
        *   Start PircBot
        *   (c) Paul Mutton 2001-2013
        *   #newbot
        */
        FidschiBot bot = new FidschiBot();
        //join channel
        System.out.println("Channel?");
        String channel = "#" + br.readLine();
        bot.joinChannel(channel);
    }
}