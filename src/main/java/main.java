import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class main {

    static String timeStamp;
    static String channel;

    static List<String> aliasL = new ArrayList<String>();
    static List<String> commandL = new ArrayList<String>();
    static List<Long> valueL = new ArrayList<Long>();

    public static void main(String[] args) throws Exception {
        //#buff
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final BufferedWriter log = new BufferedWriter(new FileWriter("log.txt"));
        //#readjson
        jsonReader.main(args);

        final FidschiBot bot = new FidschiBot();
        //join channel
        System.out.println("------------------------------------------------------");
        System.out.println("Channel?");
        channel = "#" + br.readLine();
        bot.joinChannel(channel);
        System.out.println("------------------------------------------------------");

        //timedate
        Thread BGthread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    timeStamp = new SimpleDateFormat("[HH:mm] ").format(new Date());
                }
            }
        });
        BGthread.start();

        new Timer(900000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bot.sendMessage(channel, "Ihr sucht einen Clan? -> legendarygamer.de!");
            }
        }).start();

        //save and exit
        String command = br.readLine();
        if (command.contains("s")) {

            System.out.println(aliasL);
            System.out.println(commandL);
            System.out.println(valueL);

            jsonWriter.main(args);
        } else if (command.contains("e")) {
            System.out.println("Ready for Shutdown");
            System.out.println("------------------------------------------------------");
            System.out.println(aliasL);
            System.out.println(commandL);
            System.out.println(valueL);
            System.out.println("------------------------------------------------------");
            jsonWriter.main(args);
            log.close();
            System.out.println("See you next Time");
            System.exit(0);
        }
    }
}