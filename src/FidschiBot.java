import org.jibble.pircbot.PircBot;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FidschiBot extends PircBot {

    //Create Bot / Bot settings
    public FidschiBot() throws Exception {
        this.setName("derfidschi");
        this.connect("irc.chat.twitch.tv", 6667, "oauth:wina1jfkwzqzoraqb0vqy7jes8m93l");
        this.sendRawLine("CAP REQ :twitch.tv/membership");

        this.setVerbose(false); //debug
    }

    static String timeStamp = new SimpleDateFormat("[HH:mm] ").format(new Date()); //timedate

    public void onJoin (String channel, String sender, String login, String hostname) {
        System.out.println(timeStamp + sender + " guckt zu");
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        System.out.println(timeStamp + sender + ": " + message);

        if(message.equalsIgnoreCase("!ts") || message.equalsIgnoreCase("!ts3") || message.equalsIgnoreCase("!teamspeak")) {
            sendMessage(channel, sender + " -> legendarygamer.de");
            System.out.println(timeStamp + "Antworte " + sender +" (!ts)");
        } else
        if(message.equalsIgnoreCase("!porno")) {
            jsonReader.porno++;
            sendMessage(channel, "Rob hat schon " + jsonReader.porno + " mal seinen Pornoverlauf gezeigt!");
            System.out.println(timeStamp + "Antworte " + sender + " !porno: " + (jsonReader.porno - 1) + " + 1");
        } else
        if(message.equalsIgnoreCase("!fail")) {
            jsonReader.fail++;
            sendMessage(channel, "Nicht schon wieder Rob, das war schon das " + jsonReader.porno + "te mal!");
            System.out.println(timeStamp + "Antworte " + sender + " !fail: " + (jsonReader.fail - 1) + " + 1");
        } else
        if(message.equalsIgnoreCase("!reis")) {
            jsonReader.reis++;
            sendMessage(channel, "Maschini hat schon " + jsonReader.reis + " Reiskörner gesammelt");
            System.out.println(timeStamp + "Antworte " + sender + " !reis: + " + (jsonReader.reis - 1) + " + 1");
        } else
        if(message.equalsIgnoreCase("!vac")) {
            jsonReader.vac++;
            sendMessage(channel, "ÜBERALL HACKER!! Das war schon der " + jsonReader.vac + "te!!!");
            System.out.println(timeStamp + "Antworte " + sender + " !vac: " + (jsonReader.vac - 1) + " + 1");
        } else
        if(message.equalsIgnoreCase("!vegan")) {
            jsonReader.vegan++;
            sendMessage(channel, "Srude hat schon " + jsonReader.vegan + " Vegane Schnitzel gefressen");
            System.out.println(timeStamp + "Antworte " + sender + " !vegan: " + (jsonReader.vegan - 1) + " + 1");
        }
    }
}