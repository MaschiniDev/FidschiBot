import org.jibble.pircbot.PircBot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class tBot extends PircBot {
    /*
    *   PircBot
    *   (c) Paul Mutton 2001-2013
    */
    public tBot() throws Exception {
        this.setName("derfidschi");
        this.connect("irc.chat.twitch.tv", 6667, "oauth:wina1jfkwzqzoraqb0vqy7jes8m93l");
        this.sendRawLine("CAP REQ :twitch.tv/membership");

        this.setVerbose(true); //debug
    }

    //log method -> Protokolliert Chat in Console und log.txt
    static String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    BufferedWriter log = new BufferedWriter(new FileWriter("log/" + date + ".txt"));

    public void write(String text) {
        if (main.write)
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
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread watchT = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        TimeUnit.MINUTES.sleep(1);

                        //loop to get all live user
                        for (int i = 0; i< main.viewerLive.size(); i++ ) {
                            String user = main.viewerLive.get(i); //live user from list
                            int index = main.viewerALL.indexOf(user); //position of liveUser in main list
                            int watch = main.watchtime.get(index); //get user watchtime
                            watch++; //increase points
                            main.watchtime.set(index, watch); //set new time value

                            System.out.println(user + " + 1 Minute");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ArrayIndexOutOfBoundsException be) {
                        System.out.println(be);
                    }
                }
            }
        });

        if (start) {
            //pointsT.start();
            //watchT.start();
        }
    }

    public String adminCommands(String input, String user) {
        String as = "\n";
        String[] comWords = input.split(" ");
        String response = "";

        if (main.mods.contains(user)) {
            if (comWords[0].equalsIgnoreCase("!help")) {
                String help = "Possible Commands: save, load, help, exit, list, add, remove";
                write(help);
            } else if (comWords[0].equalsIgnoreCase("!list")) {
                if (comWords[1].equalsIgnoreCase("live")) {
                    String live = "Viewercount " + main.viewerLive.size() + as + "Live viewer: " + main.viewerLive;
                    write(live); sendAction(main.channel, live);
                } else if (comWords[1].equalsIgnoreCase("all")) {
                    write(main.viewerALL.toString());
                } else if (comWords[1].equalsIgnoreCase("commands")) {
                    response = main.aliasL.toString();
                }

            } else if (comWords[0].equalsIgnoreCase("!add")) {
                //Command call (!example)
                main.aliasL.add(comWords[1]);
                //Command value for Counts, etc
                main.valueL.add(0);
                //Command Text -> Delete from Linestring all but the Text answer
                String comText = input;
                comText = comText.replace(comWords[0], "");
                comText = comText.replace(comWords[1], "");
                comText = comText.replace("  ", "");
                main.commandL.add(comText);

                String add = comWords[1] + " 0 :" + comText;
                write(add); sendAction(main.channel, add);

            } else if (comWords[0].equalsIgnoreCase("!remove")) {
                int index = main.aliasL.indexOf(comWords[1]);

                if (index == -1)
                    response = "This command dont Exist";
                else {
                    System.out.println(main.aliasL);
                    main.aliasL.remove(index);
                    main.valueL.remove(index);
                    main.commandL.remove(index);
                    System.out.println(main.aliasL);

                    response = "Removed Command " + comWords[1];
                }
            } else if (comWords[0].equalsIgnoreCase("!edit")) {
                int index = main.aliasL.indexOf(comWords[1]);
                int newVal = Integer.parseInt(comWords[2]);

                if (!input.contains("+"))
                    main.valueL.add(index, newVal);
                else if (input.contains("+")) {
                    main.valueL.add(index, main.valueL.get(index) + newVal);
                }
            }
        }

        main.write = true;
        return response;
    }
    
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        write(sender + ": " + message);
        String[] mesArr = message.split(" ");

        if(mesArr[0].equalsIgnoreCase(message)) {
            //Get Position of Command -> Set Index
            int index = main.aliasL.indexOf(message);
            String response = main.commandL.get(index);
            //Value Increase
            int value = main.valueL.get(index);
            if (response.contains("{value}")) {
                value++;
                main.valueL.set(index, value);}
            //Insert Variables
            String count = Long.toString(value);
            String antwort = response.replace("{value}", count);
            antwort = antwort.replace("{sender}", sender);
            //Send Message + Protocoll
            sendMessage(channel, antwort);
            write("Antworte " + sender + " -> " + message + " (" + value + ")");
        } else {
            sendMessage(channel, adminCommands(message, sender));
        }
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