import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

public class main {

    static String timeStamp;
    static String channel;
    static String line = "------------------------------------------------------";

    static List<String> aliasL = new ArrayList<String>();
    static List<String> commandL = new ArrayList<String>();
    static List<Long> valueL = new ArrayList<Long>();
    static List<String> LiveViewer = new ArrayList<String>();
    static List<Integer> viewerPoints = new ArrayList<Integer>(); //<- Nicht vergessen

    public static void main(String[] args) throws Exception {
        //text Strings
        //#buff
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter log = new BufferedWriter(new FileWriter("log.txt"));

        //initiate Bot
        tBot bot = new tBot();
        //join channel
        System.out.println(line);
        System.out.println("Channel?");
        channel = "#" + br.readLine();
        bot.joinChannel(channel);
        System.out.println(line);
        //#readjson
        jsonReader.main(args);

        //timedate
        Thread BGthread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    timeStamp = new SimpleDateFormat("[HH:mm] ").format(new Date());
                }
            }
        });
        BGthread.start();

        //new Timer(16000000, new ActionListener() {
        //    String tMes = "test";
        //    public void actionPerformed(ActionEvent e) {
        //        if (!tBot.lastMes.contains(tMes))
        //            bot.sendAction(channel, tMes);
        //    }
        //}).start();

        //save and exit
        while (true) {
            String command = br.readLine();
            if (command.equalsIgnoreCase("save")) {
                System.out.println(main.line);

                System.out.println(aliasL);
                System.out.println(commandL);
                System.out.println(valueL);

                jsonWriter.main(args);
            } else if (command.equalsIgnoreCase("exit")) {
                System.out.println("Ready for Shutdown");
                System.out.println(line);
                System.out.println(aliasL);
                System.out.println(commandL);
                System.out.println(valueL);
                System.out.println(line);
                jsonWriter.main(args);
                log.close();
                System.out.println("See you next Time");
                System.exit(0);
            } else if (command.equalsIgnoreCase("ga")) {
                userActions.main(args);
            }
        }
    }
}