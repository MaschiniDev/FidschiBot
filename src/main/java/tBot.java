import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

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
        if (add) {
            if (main.AllViewer.contains(user)) {
                //get User and Points
                int index = main.AllViewer.indexOf(user);
                //add user and points to Live
                main.LiveViewer.add(user);
                write("Added " + main.LiveViewer.toString() + " (Existing User)");

            } else {
                //add new user to Main list
                main.AllViewer.add(user);
                main.viewerPointsAll.add(1);
                write(main.AllViewer.toString());
                //add new user to Live list
                main.LiveViewer.add(user);
                write("Added " + main.LiveViewer.toString() + " (New User)");
            }
        } else if (!add) {
            main.LiveViewer.remove(user);
            write("Removed " + user);
        }
    }

    public static void viewPoints(boolean start) {
        Thread pointsT = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        long slep = 20;
                        TimeUnit.SECONDS.sleep(slep);

                        //loop to get all live user
                        for (int i = 0; i< main.LiveViewer.size(); i++ ) {
                            String user = main.LiveViewer.get(i); //live user from list
                            int index = main.AllViewer.indexOf(user); //position of liveUser in main list
                            int pointsU = main.viewerPointsAll.get(index); //get user points
                            pointsU++; //increase points
                            main.viewerPointsAll.set(index, pointsU); //set new points value

                            System.out.println(user + " + 1 Point");
                        }
                        System.out.println("Increased Points");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        if (start) {
            pointsT.start();
        }
        else if (!start) {
            pointsT.stop();
        }

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
            write("Antworte " + sender + " -> " + message + " " + value);
        } else if (message.contains("!me")) {
            if (main.AllViewer.contains(sender)) {
                int index = main.AllViewer.indexOf(sender);
                String pointsS = main.viewerPointsAll.get(index).toString();
                sendMessage(channel, sender + ": " + pointsS);
            }

        }
    }
    public void onJoin (String channel, String sender, String login, String hostname) {
        write("[+] " + sender + " guckt zu");
        listMod(sender, true);
    }
    public void onPart (String channel, String sender, String login, String hostname) {
        write("[-] " + sender + " guckt nicht mehr zu");
        listMod(sender, false);
        System.out.println(main.LiveViewer);

    }
}