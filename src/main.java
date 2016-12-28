import java.io.BufferedReader;
import java.io.InputStreamReader;

public class main {
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Channel?");
        String channel = "#" + br.readLine();

        FidschiBot bot = new FidschiBot();
        bot.setVerbose(true);
        bot.connect("irc.chat.twitch.tv", 6667, "oauth:wina1jfkwzqzoraqb0vqy7jes8m93l");
        bot.joinChannel(channel);

        bot.sendMessage(channel, "Der Fidschi is in da House [BOT ACTIVATED]");
    }
}