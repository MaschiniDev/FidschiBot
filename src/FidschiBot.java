import org.jibble.pircbot.PircBot;

public class FidschiBot extends PircBot {
    public FidschiBot() throws Exception {
        this.setName("derfidschi");
        this.setVerbose(true);
        this.connect("irc.chat.twitch.tv", 6667, "oauth:wina1jfkwzqzoraqb0vqy7jes8m93l");
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        if(message.equalsIgnoreCase("!ts") || message.equalsIgnoreCase("!ts3") || message.equalsIgnoreCase("!teamspeak")) {
            sendMessage(channel, sender + " -> legendarygamer.de");
        }
        if(message.equalsIgnoreCase("!test")) {
            sendMessage(channel, "test");
        }
    }
}