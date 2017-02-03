import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

public class main {

    //stuff
    static String timeStamp;
    static String channel = "maschini";
    static String line = "------------------------------------------------------ \n";
    static Boolean write = true;

    /*
    //new Object lists
    static ArrayList<Object> commandList = new ArrayList<Object>();
    static ArrayList<Object> userList = new ArrayList<Object>();
    */

    //command data
    static List<String> aliasL = new ArrayList<String>();
    static List<String> commandL = new ArrayList<String>();
    static List<Integer> valueL = new ArrayList<Integer>();

    //user data
    static List<String> viewerALL = new ArrayList<String>();
    static List<String> viewerLive = new ArrayList<String>();
    static List<Integer> viewerPoints = new ArrayList<Integer>();
    static List<Integer> watchtime = new ArrayList<Integer>();

    //moderater list -> Für admin funktionen
    static ArrayList<String> mods = new ArrayList<String>();

        public static void main(String[] args) throws Exception {
        //Thread for Protocol Time
        Thread BGthread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    timeStamp = new SimpleDateFormat("[HH:mm] ").format(new Date());
                }
            }
        });

        //#buffreader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //initiate Bot
        tBot bot = new tBot();
        //start time Thread
        BGthread.start();
        //parameter for join channel
        System.out.println(line);
        System.out.println("Channel?");
        channel = br.readLine();
        System.out.println(line);
        //#readjson
        jsonReader.main(args);
        tBot.viewPoints(true);
        //join channel
        bot.joinChannel("#" + channel);

        //#buffwriter

        //new Timer(16000000, new ActionListener() {
        //    String tMes = "test";
        //    public void actionPerformed(ActionEvent e) {
        //        if (!tBot.lastMes.contains(tMes))
        //            bot.sendAction(channel, tMes);
        //    }
        //}).start();

        //save and exit
        String as = "\n";
        while (true) {
            String lnInput = br.readLine();

            if (lnInput.contains("!")) {
                lnInput = lnInput.replace("!", "");
            }
            String[] comWords = lnInput.split(" ");

            if (comWords[0].equalsIgnoreCase("save")) {
                jsonWriter.main(args);
                System.out.println(aliasL + as + commandL + as + valueL + as + line + viewerALL + as + viewerPoints + as + line + watchtime + line + "All User: " + viewerALL.size());
            } else if (comWords[0].equalsIgnoreCase("load")) {
                try {
                    jsonReader.main(args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(aliasL + as + commandL + as + valueL + as + line + viewerALL + as + viewerPoints + as + line + watchtime + line + "All User: " + viewerALL.size());
            } else if (comWords[0].equalsIgnoreCase("exit")) {
                System.out.println("Ready for Shutdown");
                jsonWriter.main(args);
                System.out.println(aliasL + as + commandL + as + valueL + as + line + as + viewerALL + as + viewerPoints + as + line + as + "See you next Time");
                System.exit(0);
            }
        }
    }
}