import java.io.BufferedReader;
import java.io.InputStreamReader;

public class main {
    public static void main(String[] args) throws Exception {

        //#buffreader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        /*
        *   Start PircBot
        *   (c) Paul Mutton 2001-2013
        *   #newbot
        */
        System.out.println("Channel?");
        String channel = "#" + br.readLine();

        //define bot
        FidschiBot bot = new FidschiBot();
        bot.joinChannel(channel);
        //bot.sendMessage(channel, "[BOT ACTIVATED]");
    }
}