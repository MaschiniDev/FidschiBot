import org.jibble.pircbot.PircBot;

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

    public void onJoin (String channel, String sender, String login, String hostname) {
        System.out.println(main.timeStamp + "[+] " + sender + " guckt zu");
    }
    public void onPart (String channel, String sender, String login, String hostname) {
        System.out.println(main.timeStamp + "[-] " + sender + " guckt nicht mehr zu");
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        System.out.println(main.timeStamp + sender + ": " + message);
        //nachricht für die logs


        if(jsonReader.aliaseL.contains(message)) {
            System.out.println("# " + message);
            int index = jsonReader.aliaseL.indexOf(message);

            System.out.println("# " + index);
            String response = jsonReader.commandL.get(index);

            long value = jsonReader.valueL.get(index);
            value++;
            jsonReader.valueL.set(index, value);

            String count = Long.toString(value);
            String antwort = response.replace("{value}", count);
            antwort = antwort.replace("{sender}", sender);

            System.out.println("# " + antwort);
            sendMessage(channel, antwort);
        }
        //String sysmes = main.timeStamp + "Antworte " + sender;
        //String curval = jsonReader.porno - 1 + " + 1";

        /**
         * if(message.equalsIgnoreCase("!ts") || message.equalsIgnoreCase("!ts3") || message.equalsIgnoreCase("!teamspeak")) {
        *    sendMessage(channel, sender + " -> legendarygamer.de");
        *    System.out.println(sysmes + " (!ts)");
        *} else
        *if(message.equalsIgnoreCase("!porno")) {
        *    jsonReader.porno++;
        *    sendMessage(channel, "Rob hat schon " + jsonReader.porno + " mal seinen Pornoverlauf gezeigt!");
        *    System.out.println(sysmes + " !porno: " + curval);
        *} else
        *if(message.equalsIgnoreCase("!fail")) {
        *    jsonReader.fail++;
        *    sendMessage(channel, "Nicht schon wieder Rob, das war schon das " + jsonReader.porno + "te mal!");
        *    System.out.println(sysmes + " !fail: " + curval);
        *} else
        *if(message.equalsIgnoreCase("!reis")) {
        *    jsonReader.reis++;
        *    sendMessage(channel, "Maschini hat schon " + jsonReader.reis + " Reiskörner gesammelt");
        *    System.out.println(sysmes + " !reis: + " + curval);
        *} else
        *if(message.equalsIgnoreCase("!vac")) {
        *    jsonReader.vac++;
        *    sendMessage(channel, "ÜBERALL HACKER!! Das war schon der " + jsonReader.vac + "te!!!");
        *    System.out.println(sysmes + " !vac: " + curval);
        *} else
        *if(message.equalsIgnoreCase("!vegan")) {
        *    jsonReader.vegan++;
        *    sendMessage(channel, "Srude hat schon " + jsonReader.vegan + " Vegane Schnitzel gefressen");
        *    System.out.println(sysmes + " !vegan: " + curval);
        *}
        */
    }
}