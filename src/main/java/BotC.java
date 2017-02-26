import org.jibble.pircbot.PircBot;

public class BotC extends PircBot {
    /*
    *   PircBot
    *   (c) Paul Mutton 2001-2013
    */
    public BotC() throws Exception {
        this.setName("derfidschi");
        this.connect("irc.chat.twitch.tv", 6667, "oauth:wina1jfkwzqzoraqb0vqy7jes8m93l");
        this.sendRawLine("CAP REQ :twitch.tv/membership");

        this.setVerbose(false); //debug
    }

    private void listMod(String user, boolean add) {
        if (!Lists.viewerLive.contains(user)) {
            if (add) {
                    if (Lists.viewerALL.contains(user)) {
                        //add user and points to Live
                        if (!Lists.viewerLive.contains(user)) {
                            Lists.viewerLive.add(user);
                            Log.write("Added " + user + " (Existing User)");
                        }
                    } else {
                        //add new user to Main Lists
                        Lists.viewerALL.add(user);
                        Lists.viewerPoints.add(1);
                        //add new user to Live Lists
                        Lists.viewerLive.add(user);
                        Log.write("Added " + user + " (New User)");
                    }
            } else if (!add) {
                Lists.viewerLive.remove(user);
                Log.write("Removed " + user);
            }
        }
    }

    public String systemCommands(String input, String user) {
        String as = "\n";
        String[] comWords = input.split(" ");
        String response = "";

        if (Lists.mods.contains(user)) {
            if (comWords[0].equalsIgnoreCase("!help")) {
                String help = "Possible Commands: save, load, help, exit, Lists, add, remove";
                Log.write(help);
            } else if (comWords[0].equalsIgnoreCase("!Lists")) {
                if (comWords[1].equalsIgnoreCase("live")) {
                    String live = "Viewercount " + Lists.viewerLive.size() + as + "Live viewer: " + Lists.viewerLive;
                    Log.write(live); sendAction(Lists.channel, live);
                } else if (comWords[1].equalsIgnoreCase("all")) {
                    Log.write(Lists.viewerALL.toString());
                } else if (comWords[1].equalsIgnoreCase("commands")) {
                    response = Lists.alias.toString();
                }

            } else if (comWords[0].equalsIgnoreCase("!add")) {
                //Command call (!example)
                Lists.alias.add(comWords[1].toLowerCase());
                //Command value for Counts, etc
                Lists.values.add(0);
                //Command Text -> Delete from Linestring all but the Text answer
                String comText = input;
                comText = comText.replace(comWords[0], "");
                comText = comText.replace(comWords[1], "");
                comText = comText.replace("  ", "");
                Lists.commands.add(comText);

                String add = comWords[1] + " 0 :" + comText;
                Log.write(add); sendAction("+" + Lists.channel, add);

            } else if (comWords[0].equalsIgnoreCase("!remove")) {
                int index = Lists.alias.indexOf(comWords[1]);

                if (index == -1)
                    response = "This command dont Exist";
                else {
                    System.out.println(Lists.alias);
                    Lists.alias.remove(index);
                    Lists.values.remove(index);
                    Lists.commands.remove(index);
                    System.out.println(Lists.alias);

                    response = "Removed Command " + comWords[1];
                }
            } else if (comWords[0].equalsIgnoreCase("!edit")) {
                int index = Lists.alias.indexOf(comWords[1]);
                int newVal = Integer.parseInt(comWords[2]);

                if (!input.contains("+"))
                    Lists.values.add(index, newVal);
                else if (input.contains("+")) {
                    Lists.values.add(index, Lists.values.get(index) + newVal);
                }
            }
        }
        if (comWords[0].equals("!gamble")) {
            int pointstoGamble = Integer.parseInt(comWords[1]);
            int index = Lists.viewerALL.indexOf(user);
            int winnedPoints = 0;

            String mes = "";

            System.out.println(Lists.viewerPoints.get(index));

            if (Lists.viewerPoints.get(index) < pointstoGamble) {
                return "Du hast zu wenig Punkte";
            } else if (Lists.viewerPoints.get(index) >= pointstoGamble) {
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
                Lists.viewerPoints.add(Lists.viewerPoints.get(index) + winnedPoints);
                return mes;
            }
        }

        Lists.write = true;
        return response;
    }
    
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        Log.write(sender + ": " + message);
        message = message.toLowerCase();
        String[] mesArr = message.split(" ");

        if(mesArr[0].toLowerCase().equals(message)) {
            //Get Position of Command -> Set Index
            int indexCom = Lists.alias.indexOf(message);
            String response = Lists.commands.get(indexCom);
            //Value Increase
            int value = Lists.values.get(indexCom);
            if (response.contains("{value}")) {
                value++;
                Lists.values.set(indexCom, value);}
            //Insert Variables
            String count = Long.toString(value);
            String antwort = response.replace("{value}", count);
            antwort = antwort.replace("{sender}", sender);
            antwort = antwort.replace("{points}", Lists.viewerPoints.get(Lists.viewerALL.indexOf(sender)).toString());
            //Send Message + Protocoll
            sendMessage(channel, antwort);
            Log.write("Antworte " + sender + " -> " + message + " (" + value + ")");
        } else {
            sendMessage(channel, systemCommands(message, sender));
        }
    }

    public void onJoin (String channel, String sender, String login, String hostname) {
        Log.write("[+] " + sender + " guckt zu");
        listMod(sender, true);
    }
    public void onPart (String channel, String sender, String login, String hostname) {
        Log.write("[-] " + sender + " guckt nicht mehr zu");
        listMod(sender, false);
    }
}