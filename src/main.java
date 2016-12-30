import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class main {
    public static void main(String[] args) throws Exception {

        boolean loop = true;


        //#buffreader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //#readjson
        jsonReader.main(args);

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

        String command = br.readLine();
        if (command.contains("s"))
            jsonWriter.main(args);
        else if (command.contains("e")) {
            jsonWriter.main(args);
            System.exit(0);
        }

        //auto save
        while (loop == true) {
            TimeUnit.MINUTES.sleep(5);
            jsonWriter.main(args);
        }
    }
}