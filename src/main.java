import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class main {

    static String timeStamp;

    public static void main(String[] args) throws Exception {
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
        System.out.println("------------------------------------------------------");
        System.out.println("Channel?");
        String channel = "#" + br.readLine();
        bot.joinChannel(channel);
        System.out.println("------------------------------------------------------");

        //timedate
        Thread BGthread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    timeStamp = new SimpleDateFormat("[HH:mm] ").format(new Date());
                }
            }
        });
        BGthread.start();

        String command = br.readLine();
        if (command.contains("s"))
            jsonWriter.main(args);
        else if (command.contains("e")) {
            System.out.println("------------------------------------------------------");
            System.out.println("Shutdown Bot");
            jsonWriter.main(args);
            System.exit(0);
        }
    }
}