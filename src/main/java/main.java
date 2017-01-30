import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

public class main {

    //stuff
    static String timeStamp;
    static String channel = "robzocker07";
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

    //user blacklist -> Diese user werden nicht in die viewerliste hinzugefügt
    //static ArrayList<String> blacklist = new ArrayList<String>();


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
        String as = " \n ";
        while (true) {
            String command = br.readLine();
            String[] comWords = command.split(" ");

            if (comWords[0].equalsIgnoreCase("save")) {
                jsonWriter.main(args);
                System.out.println(aliasL + as + commandL + as + valueL + as + line + viewerALL + as + viewerPoints + as + line + watchtime + line + "All User: " + viewerALL.size());
            } else if (comWords[0].equalsIgnoreCase("load")) {
                jsonReader.main(args);
                System.out.println(aliasL + as + commandL + as + valueL + as + line + viewerALL + as + viewerPoints + as + line + watchtime + line + "All User: " + viewerALL.size());
            } else if (comWords[0].equalsIgnoreCase("help")) {
                System.out.println("Possible Commands: save, load, help, exit, list, add, remove");
            } else if (comWords[0].equalsIgnoreCase("exit")) {
                System.out.println("Ready for Shutdown");
                jsonWriter.main(args);
                System.out.println(aliasL + as + commandL + as + valueL + as + line + as + viewerALL + as + viewerPoints + as + line);
                System.out.println("See you next Time");
                System.exit(0);
            } else if (comWords[0].equalsIgnoreCase("list")) {
                if (comWords[1].equalsIgnoreCase("live")) {
                    System.out.println("Viewercount " + viewerLive.size());
                    System.out.println("Live viewer: " + viewerLive);
                } else if (comWords[1].equalsIgnoreCase("all")) {
                    for (int i = 0 ; i< main.viewerALL.size() ; i++) {
                        System.out.println(viewerALL.get(i) + ", " + viewerPoints.get(i));
                    }
                } else if (comWords[1].equalsIgnoreCase("alias")) {
                    System.out.println(aliasL);
                }

            } else if (comWords[0].equalsIgnoreCase("add")) {
                if (comWords[1].equalsIgnoreCase("com") || comWords[1].equalsIgnoreCase("command")) {
                    aliasL.add(comWords[2]);
                    valueL.add(Integer.parseInt(comWords[3]));
                    commandL.add(comWords[4]);

                    System.out.println(comWords[2] + " " + comWords[3] + " " + comWords[4]);
                } else if (comWords[1].equalsIgnoreCase("help")) {
                    System.out.println("Write: com !testcommand \"customvalue, wenn keins benötigt schreibe 0\" \"Mustertext\"");
                }
            } else if (comWords[0].equalsIgnoreCase("remove")) {
                    System.out.println("Remove");
                    int index = aliasL.indexOf(comWords[1]);

                    if (index == -1)
                        System.out.println("-1");
                    else {
                        System.out.println(aliasL);
                        aliasL.remove(index);
                        valueL.remove(index);
                        commandL.remove(index);
                        System.out.println(aliasL);
                    }
            }
            write = true;
        }
    }
}