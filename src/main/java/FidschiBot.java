import org.jibble.pircbot.PircBot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FidschiBot extends PircBot {
    /*
    *   PircBot
    *   (c) Paul Mutton 2001-2013
    */
    public FidschiBot() throws Exception {
        this.setName("derfidschi");
        this.connect("irc.chat.twitch.tv", 6667, "oauth:wina1jfkwzqzoraqb0vqy7jes8m93l");
        this.sendRawLine("CAP REQ :twitch.tv/membership");

        this.setVerbose(false); //debug
    }

    BufferedWriter log = new BufferedWriter(new FileWriter("log.txt"));
    private void write(String text) {
        System.out.println(text);
        try {
            log.newLine();
            log.write(text);
            log.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        write(main.timeStamp + sender + ": " + message);

        if(main.aliasL.contains(message)) {
            System.out.println("# " + message);
            int index = main.aliasL.indexOf(message);

            System.out.println("# " + index);
            String response = main.commandL.get(index);

            long value = main.valueL.get(index);
            value++;
            main.valueL.set(index, value);

            String count = Long.toString(value);
            String antwort = response.replace("{value}", count);
            antwort = antwort.replace("{sender}", sender);

            System.out.println("# " + antwort);
            sendMessage(channel, antwort);

            write(main.timeStamp + "Antworte " + sender + " -> " + message + " " + value);
        }
    }
    public void onJoin (String channel, String sender, String login, String hostname) {
        write(main.timeStamp + "[+] " + sender + " guckt zu");

    }
    public void onPart (String channel, String sender, String login, String hostname) {
        write(main.timeStamp + "[-] " + sender + " guckt nicht mehr zu");
    }
}