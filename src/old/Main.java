import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

public class Main {

        public static void main(String[] args) throws Exception {
        //Thread for Protocol Time
        Thread BGthread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    Lists.timeStamp = new SimpleDateFormat("[HH:mm] ").format(new Date());
                }
            }
        });

        //#buffreader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //initiate Bot
        TBot bot = new TBot();
        //parameter for join channel
        System.out.println(Lists.line);

        System.out.println("Channel?");
        Lists.channel = br.readLine();

        System.out.println(Lists.line);
        //#readjson
        Json.Reader(); //jsonReader.Main(args);
        //join channel
        bot.joinChannel("#" + Lists.channel);

        //start Threads
        Threads.autoSave(true);
        Threads.viewerPoints(true);
        Threads.watchTime(true);
        BGthread.start();

        //save and exit
        String as = "\n";
        while (true) {
            String lnInput = br.readLine();

            if (lnInput.contains("!")) {
                lnInput = lnInput.replace("!", "");
            }
            String[] comWords = lnInput.split(" ");

            if (comWords[0].equalsIgnoreCase("Lists")) {
                System.out.println("Live: " + Lists.viewerLive.toString() + as + "All: " + Lists.viewerALL.toString());
            } else if (comWords[0].equalsIgnoreCase("save")) {
                Json.Writer(); //jsonWriter.Main(args);
                System.out.println(Lists.alias + as + Lists.commands + as + Lists.values + as + Lists.line + Lists.viewerALL + as + Lists.viewerPoints + as + Lists.line + Lists.watchtime + Lists.line + "All User: " + Lists.viewerALL.size());
            } else if (comWords[0].equalsIgnoreCase("load")) {
                try {
                    Json.Reader(); //jsonReader.Main(args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Lists.alias + as + Lists.commands + as + Lists.values + as + Lists.line + Lists.viewerALL + as + Lists.viewerPoints + as + Lists.line + Lists.watchtime + Lists.line + "All User: " + Lists.viewerALL.size());
            } else if (comWords[0].equalsIgnoreCase("exit")) {
                System.out.println("Ready for Shutdown");
                Json.Writer(); //jsonWriter.Main(args);
                System.out.println(Lists.alias + as + Lists.commands + as + Lists.values + as + Lists.line + as + Lists.viewerALL + as + Lists.viewerPoints + as + Lists.line + as + "See you next Time");
                System.exit(0);
            }
        }
    }
}