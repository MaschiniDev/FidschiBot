import org.jibble.pircbot.PircBot;

public class FidschiBot extends PircBot {
    public FidschiBot() {
        this.setName("derfidschi");
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        if(message.equalsIgnoreCase("!ts")) {
            System.out.println("lg.de");
        }
    }
}