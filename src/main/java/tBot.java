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

        this.setVerbose(false); //debug
    }

    private void listMod(String user, boolean add) {
        if (!list.viewerLive.contains(user)) {
            if (add) {
                    if (list.viewerALL.contains(user)) {
                        //add user and points to Live
                        if (!list.viewerLive.contains(user)) {
                            list.viewerLive.add(user);
                            log.write("Added " + user + " (Existing User)");
                        }
                    } else {
                        //add new user to Main list
                        list.viewerALL.add(user);
                        list.viewerPoints.add(1);
                        //add new user to Live list
                        list.viewerLive.add(user);
                        log.write("Added " + user + " (New User)");
                    }
            } else if (!add) {
                list.viewerLive.remove(user);
                log.write("Removed " + user);
            }
        }
    }

    public String systemCommands(String input, String user) {
        String as = "\n";
        String[] comWords = input.split(" ");
        String response = "";

        if (list.mods.contains(user)) {
            if (comWords[0].equalsIgnoreCase("!help")) {
                String help = "Possible Commands: save, load, help, exit, list, add, remove";
                log.write(help);
            } else if (comWords[0].equalsIgnoreCase("!list")) {
                if (comWords[1].equalsIgnoreCase("live")) {
                    String live = "Viewercount " + list.viewerLive.size() + as + "Live viewer: " + list.viewerLive;
                    log.write(live); sendAction(list.channel, live);
                } else if (comWords[1].equalsIgnoreCase("all")) {
                    log.write(list.viewerALL.toString());
                } else if (comWords[1].equalsIgnoreCase("commands")) {
                    response = list.alias.toString();
                }

            } else if (comWords[0].equalsIgnoreCase("!add")) {
                //Command call (!example)
                list.alias.add(comWords[1].toLowerCase());
                //Command value for Counts, etc
                list.values.add(0);
                //Command Text -> Delete from Linestring all but the Text answer
                String comText = input;
                comText = comText.replace(comWords[0], "");
                comText = comText.replace(comWords[1], "");
                comText = comText.replace("  ", "");
                list.commands.add(comText);

                String add = comWords[1] + " 0 :" + comText;
                log.write(add); sendAction("+" + list.channel, add);

            } else if (comWords[0].equalsIgnoreCase("!remove")) {
                int index = list.alias.indexOf(comWords[1]);

                if (index == -1)
                    response = "This command dont Exist";
                else {
                    System.out.println(list.alias);
                    list.alias.remove(index);
                    list.values.remove(index);
                    list.commands.remove(index);
                    System.out.println(list.alias);

                    response = "Removed Command " + comWords[1];
                }
            } else if (comWords[0].equalsIgnoreCase("!edit")) {
                int index = list.alias.indexOf(comWords[1]);
                int newVal = Integer.parseInt(comWords[2]);

                if (!input.contains("+"))
                    list.values.add(index, newVal);
                else if (input.contains("+")) {
                    list.values.add(index, list.values.get(index) + newVal);
                }
            }
        }
        if (comWords[0].equals("!gamble")) {
            int pointstoGamble = Integer.parseInt(comWords[1]);
            int index = list.viewerALL.indexOf(user);
            int winnedPoints = 0;

            String mes = "";

            System.out.println(list.viewerPoints.get(index));

            if (list.viewerPoints.get(index) < pointstoGamble) {
                return "Du hast zu wenig Punkte";
            } else if (list.viewerPoints.get(index) >= pointstoGamble) {
                int winNum = (int) (Math.random() * 100);
                if (winNum < 60) {
                    winnedPoints = -pointstoGamble;
                    mes = "Rolled " + winNum + ". Du hast " + winnedPoints + " verloren";
                } else if (winNum > 60) {
                    if (winNum > 90) {
                        winnedPoints = pointstoGamble * 3;
                        mes = "Rolled " + winNum + ". Du hast " + winnedPoints + " gewonnen";
                    } else {
                        winnedPoints = pointstoGamble * 2;
                        mes = "Rolled " + winNum + ". Du hast " + winnedPoints + " gewonnen";
                    }
                }
                list.viewerPoints.add(list.viewerPoints.get(index) + winnedPoints);
                return mes;
            }
        }

        list.write = true;
        return response;
    }
    
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        log.write(sender + ": " + message);
        message = message.toLowerCase();
        String[] mesArr = message.split(" ");

        if(mesArr[0].toLowerCase().equals(message)) {
            //Get Position of Command -> Set Index
            int indexCom = list.alias.indexOf(message);
            String response = list.commands.get(indexCom);
            //Value Increase
            int value = list.values.get(indexCom);
            if (response.contains("{value}")) {
                value++;
                list.values.set(indexCom, value);}
            //Insert Variables
            String count = Long.toString(value);
            String antwort = response.replace("{value}", count);
            antwort = antwort.replace("{sender}", sender);
            antwort = antwort.replace("{points}", list.viewerPoints.get(list.viewerALL.indexOf(sender)).toString());
            //Send Message + Protocoll
            sendMessage(channel, antwort);
            log.write("Antworte " + sender + " -> " + message + " (" + value + ")");
        } else {
            sendMessage(channel, systemCommands(message, sender));
        }
    }

    public void onJoin (String channel, String sender, String login, String hostname) {
        log.write("[+] " + sender + " guckt zu");
        listMod(sender, true);
    }
    public void onPart (String channel, String sender, String login, String hostname) {
        log.write("[-] " + sender + " guckt nicht mehr zu");
        listMod(sender, false);
    }
}