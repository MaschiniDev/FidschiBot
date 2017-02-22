import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

public class main {

        public static void main(String[] args) throws Exception {
        //Thread for Protocol Time
        Thread BGthread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    list.timeStamp = new SimpleDateFormat("[HH:mm] ").format(new Date());
                }
            }
        });

        //#buffreader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //initiate Bot
        tBot bot = new tBot();
        //parameter for join channel
        System.out.println(list.line);

        System.out.println("Channel?");
        list.channel = br.readLine();

        System.out.println(list.line);
        //#readjson
        json.Reader(); //jsonReader.main(args);
        //join channel
        bot.joinChannel("#" + list.channel);

        //start Threads
        threads.autoSave(true);
        threads.viewerPoints(true);
        threads.watchTime(true);
        BGthread.start();

        //save and exit
        String as = "\n";
        while (true) {
            String lnInput = br.readLine();

            if (lnInput.contains("!")) {
                lnInput = lnInput.replace("!", "");
            }
            String[] comWords = lnInput.split(" ");

            if (comWords[0].equalsIgnoreCase("list")) {
                System.out.println("Live: " + list.viewerLive.toString() + as + "All: " + list.viewerALL.toString());
            } else if (comWords[0].equalsIgnoreCase("save")) {
                json.Writer(); //jsonWriter.main(args);
                System.out.println(list.alias + as + list.commands + as + list.values + as + list.line + list.viewerALL + as + list.viewerPoints + as + list.line + list.watchtime + list.line + "All User: " + list.viewerALL.size());
            } else if (comWords[0].equalsIgnoreCase("load")) {
                try {
                    json.Reader(); //jsonReader.main(args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(list.alias + as + list.commands + as + list.values + as + list.line + list.viewerALL + as + list.viewerPoints + as + list.line + list.watchtime + list.line + "All User: " + list.viewerALL.size());
            } else if (comWords[0].equalsIgnoreCase("exit")) {
                System.out.println("Ready for Shutdown");
                json.Writer(); //jsonWriter.main(args);
                System.out.println(list.alias + as + list.commands + as + list.values + as + list.line + as + list.viewerALL + as + list.viewerPoints + as + list.line + as + "See you next Time");
                System.exit(0);
            }
        }
    }
}