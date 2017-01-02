import org.jibble.pircbot.PircBot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
    private void write(String text) {
        System.out.println(main.timeStamp + text);
        try {
            log.newLine();
            log.write(main.timeStamp + text);
            log.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
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
            long value = main.valueL.get(index);
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
        }
    }
    public void onJoin (String channel, String sender, String login, String hostname) {
        write("[+] " + sender + " guckt zu");
        main.LiveViewer.add(login);
        System.out.println(main.LiveViewer);
    }
    public void onPart (String channel, String sender, String login, String hostname) {
        write("[-] " + sender + " guckt nicht mehr zu");
        main.LiveViewer.remove(login);
        System.out.println(main.LiveViewer);

    }
}