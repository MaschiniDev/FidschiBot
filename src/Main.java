import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        Bot bot = new Bot();

        System.out.println(Data.line + "\nChannel?");
        String channel = console.readLine();

        Json.Reader(channel);
        bot.joinChannel("#" + channel);

        String exit = console.readLine();
        if (exit != null) {
            Json.Writer(channel);
            System.exit(0);
        }
    }
}
