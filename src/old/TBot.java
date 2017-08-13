import org.jibble.pircbot.PircBot;

public class TBot extends PircBot{
    public TBot() throws Exception {
        this.setName("derfidschi");
        this.connect("irc.chat.twitch.tv", 6667, "oauth:wina1jfkwzqzoraqb0vqy7jes8m93l");
        this.sendRawLine("CAP REQ :twitch.tv/membership");

        this.setVerbose(false); //debug
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        sendMessage(channel, cmdResponse(sender, message));
    }//test

    static String cmdResponse(String user, String input) {
        String[] splittedCommand = input.split(" ");

        String cmdAlias = splittedCommand[0];
        String cmdContext = input.replace(splittedCommand[0] + " ", "");
        String response = "";

        if (Lists.mods.contains(user)) {
            response = systemCmd(cmdAlias, cmdContext, splittedCommand);
        }

        System.out.print(response);
        return response;
    }

    static public String systemCmd(String alias, String context, String[] splCmd) { //TODO Neue Bot Class
        String response = "";

        if (alias.equals("!help")) {
            if (splCmd[1] == null) {
                response = "Verfügbare Optionen: live, all, commands";
                System.out.print("test");
            } else if (splCmd[1].equals("live")) {
                response = Lists.viewerLive.toString().replace("[", "").replace("]", "");
            } else if (splCmd[1].equals("all")) {
                response = Lists.viewerALL.toString().replace("[", "").replace("]", "");
            } else if (splCmd[1].equals("commands")) {
                response = Lists.alias.toString().replace("[", "").replace("]", "");
            }
        } else if (alias.equals("!add")) {
            String newAlias = splCmd[1];
            String newContext = context.replace(splCmd[1] + " ", "");

            if (!newAlias.contains("!"))
                newAlias = "!" + newAlias;

            Lists.alias.add(newAlias);
            Lists.values.add(0);
            Lists.commands.add(newContext);
        } else if (alias.equals("!remove")) {
            String rmCmd = splCmd[1];

            if (!rmCmd.contains("!")) {
                rmCmd = "!" + rmCmd;
            }

            int index = Lists.alias.indexOf(rmCmd);

            if (index == -1) {
                response = "This command dont Exist";
            } else {
                Lists.alias.remove(index);
                Lists.values.remove(index);
                Lists.commands.remove(index);

                response = "Removed Command " + rmCmd;
            }
        } else if (alias.equals("!edit")) {
            int index = Lists.alias.indexOf(splCmd[1]);
            int newVal = Integer.parseInt(splCmd[2]);

            if (!context.contains("+"))
                Lists.values.add(index, newVal);
            else if (context.contains("+")) {
                Lists.values.add(index, Lists.values.get(index) + newVal);
            }
        }
        else {
            response = "No Command Matched (System)";
        }
        return response;
    }
}