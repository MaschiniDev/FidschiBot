import java.io.BufferedReader;
import java.io.InputStreamReader;

public class config {
    public static void main(String[] args) throws Exception {

        String channel = "#robzocker07";
        String st = "";

        boolean isConnected = true;



        //define Bot
        FidschiBot bot = new FidschiBot();

        //debug
        bot.setVerbose(true);

        bot.setVerbose(true);
        bot.connect("irc.chat.twitch.tv", 6667, "oauth:wina1jfkwzqzoraqb0vqy7jes8m93l");
        bot.joinChannel(channel);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (isConnected == true){
            System.out.print("Enter String - ");
            String s = br.readLine();

            bot.sendMessage("#robzocker07", s);

        }
    }
}