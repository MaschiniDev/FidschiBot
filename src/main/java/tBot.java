import org.jibble.pircbot.PircBot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class tBot extends PircBot {
    static String lastMes;
    /*
    *   PircBot
    *   (c) Paul Mutton 2001-2013
    */
    public tBot() throws Exception {
        this.setName("derfidschi");
        this.connect("irc.chat.twitch.tv", 6667, "oauth:wina1jfkwzqzoraqb0vqy7jes8m93l");
        this.sendRawLine("CAP REQ :twitch.tv/membership");

        this.setVerbose(false); //debug
    }

    //log method -> Protokolliert Chat in Console und log.txt
    BufferedWriter log = new BufferedWriter(new FileWriter("log.txt"));
    public void write(String text) {
        System.out.println(main.timeStamp + text);
        try {
            log.newLine();
            log.write(main.timeStamp + text);
            log.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void listMod(String user, boolean add) {
        if (!main.viewerLive.contains(user)) {
            if (add) {
                //if (!main.blacklist.contains(user)) {
                    if (main.viewerALL.contains(user)) {
                        //add user and points to Live
                        if (!main.viewerLive.contains(user)) {
                            main.viewerLive.add(user);
                            write("Added " + user + " (Existing User)");
                        }
                    } else {
                        //add new user to Main list
                        main.viewerALL.add(user);
                        main.viewerPoints.add(1);
                        //add new user to Live list
                        main.viewerLive.add(user);
                        write("Added " + user + " (New User)");
                    }
                //}
            } else if (!add) {
                main.viewerLive.remove(user);
                write("Removed " + user);
            }
        }
    }

    public static void viewPoints(boolean start) {
        Thread pointsT = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        TimeUnit.MINUTES.sleep(5);

                        //loop to get all live user
                        for (int i = 0; i< main.viewerLive.size(); i++ ) {
                            String user = main.viewerLive.get(i); //live user from list
                            int index = main.viewerALL.indexOf(user); //position of liveUser in main list
                            int pointsU = main.viewerPoints.get(index); //get user points
                            pointsU++; //increase points
                            main.viewerPoints.set(index, pointsU); //set new points value

                            System.out.println(user + " + 1 Point");
                        }
                        System.out.println("Increased Points");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //#watchtime -> need new json system
        //Thread watchT = new Thread(new Runnable() {
        //    public void run() {
        //        while (true) {
        //            try {
        //                TimeUnit.MINUTES.sleep(1);
//
        //                //loop to get all live user
        //                for (int i = 0; i< main.viewerLive.size(); i++ ) {
        //                    String user = main.viewerLive.get(i); //live user from list
        //                    int index = main.viewerALL.indexOf(user); //position of liveUser in main list
        //                    int pointsU = main.viewerPoints.get(index); //get user points
        //                    pointsU++; //increase points
        //                    main.viewerPoints.set(index, pointsU); //set new points value
//
        //                    System.out.println(user + " + 1 Point");
        //                }
        //                System.out.println("Increased Points");
        //            } catch (InterruptedException e) {
        //                e.printStackTrace();
        //            }
        //        }
        //    }
        //})

        if (start) {
            pointsT.start();
        }
        //else if (!start) {
        //    pointsT.stop();
        //}
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        write(sender + ": " + message);
        lastMes = message;

        if(main.aliasL.contains(message)) {
            //Get Position of Command -> Set Index
            int index = main.aliasL.indexOf(message);
            String response = main.commandL.get(index);
            //Value Increase
            int value = main.valueL.get(index);
            if (response.toLowerCase().indexOf("{value}") != -1) {
                value++;
                main.valueL.set(index, value);}
            //Insert Variables
            String count = Long.toString(value);
            String antwort = response.replace("{value}", count);
            antwort = antwort.replace("{sender}", sender);
            //Send Message + Protocoll
            sendMessage(channel, antwort);
            write("Antworte " + sender + " -> " + message + " (" + value + ")");
        } else if (message.contains("!me")) {
            if (main.viewerALL.contains(sender)) {
                int index = main.viewerALL.indexOf(sender);
                String pointsS = main.viewerPoints.get(index).toString();
                int watchtime = main.viewerPoints.get(index);

                sendMessage(channel, sender + " Viewerpoints: " + pointsS + ", Watchtime: " + (watchtime * 5) + " Minuten");
            }
        }

        if (!main.viewerALL.contains(sender))
            write("[+] " + sender + " guckt zu");
            listMod(sender, true);
    }
    public void onJoin (String channel, String sender, String login, String hostname) {
            write("[+] " + sender + " guckt zu");
            listMod(sender, true);
    }
    public void onPart (String channel, String sender, String login, String hostname) {
        write("[-] " + sender + " guckt nicht mehr zu");
        listMod(sender, false);
    }
}