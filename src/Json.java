import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;


class Json {
    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            try {
                URL url = new URL(urlString);
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
            } catch (IOException ioe) {
                System.out.println(ioe);
                URL url = new URL(urlString);
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
            }
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
    private static void save(String data, String file) {
        try {
            FileWriter fW = new FileWriter(file);
            fW.write(data);
            fW.flush();
            fW.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    static void Reader (String channel){
        System.out.println("Start jsonReader.Main");
        String sURL = "http://tmi.twitch.tv/group/user/" + channel + "/chatters";
        String file = channel + ".Json" /*"maschini.Json"*/;
        JSONParser parser = new JSONParser();

        /*
        Get viewer infos from twitch
         */
        try {
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
        } catch (IOException ioe){
            System.out.println(ioe);
        }

        try {
            Object obj = parser.parse(readUrl(sURL));
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject chatters = (JSONObject) jsonObject.get("chatters");
            JSONArray viewers = (JSONArray) chatters.get("viewers");

            Data.live = Integer.parseInt ((((JSONObject) obj).get("chatter_count").toString()));
            System.out.println(Data.live);

            for (int i = 0; i < viewers.size(); i++) {
                if (Data.user.containsKey(viewers.get(i))) {
                    Data.viewer.add(viewers.get(i).toString());
                } else if (!Data.user.containsKey(viewers.get(i))) {
                    //Add new user to Map
                    HashMap<String, Integer> userStats = new HashMap<String, Integer>();
                        userStats.put("time", 0);
                        userStats.put("points", 0);

                    Data.user.put(viewers.get(i).toString(), userStats);
                    //Add user to Live
                    Data.viewer.add(viewers.get(i).toString());
                }
            }
            /*
            TODO Add Mods to Lists (old: Create Blacklist)
             */
            //JSONArray mArr = (JSONArray) chatters.get("moderators");
            //System.out.println("Mods: " + mArr);
            //Lists.mods.add(Lists.channel); Lists.mods.add("maschini");
            //for (int i = 0; i < mArr.size(); i++) {
            //    String mods = mArr.get(i).toString();
            //    Lists.mods.add(mods);
            //}
            } catch (Exception e) {
                e.printStackTrace();
        }

        /*
        get viewer infos from local Data
         */
        try {
            File f = new File(file);
            if(!f.exists()) {
                try {
                    FileWriter fW = new FileWriter(channel + ".Json" /*"maschini.Json"*/);
                    fW.write("{\"commands\":[],\"users\":[]}");
                    fW.flush();
                    fW.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            Object obj = parser.parse(new FileReader(file));
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray commands = (JSONArray) jsonObject.get("commands");
            Iterator it = commands.iterator();
            for (int i = 0; i < commands.size(); i++) {
                JSONObject command = (JSONObject) it.next();

                String alias = (String)command.get("alias");
                String commandS = (String)command.get("command");
                Integer value = ((Long)command.get("value")).intValue();

                Lists.alias.add(alias);
                Lists.commands.add(commandS);
                Lists.values.add(value);

                System.out.println(alias + commandS + value);
            }
            JSONArray users = (JSONArray) jsonObject.get("users");
            if (users != null) {
                for (int i = 0; i < users.size(); i++) {
                    JSONObject selectedUser = (JSONObject) users.get(i);
                    String username = selectedUser.get("user").toString();

                    HashMap<String, Integer> user = Data.user.get(username);
                    user.put("time", ((Long) selectedUser.get("watchtime")).intValue());
                    user.put("points", ((Long) selectedUser.get("points")).intValue());

                    Data.user.put(username, user);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void Writer () {
        /*
        Arrays and Objects
         */
        JSONArray comArr = new JSONArray();
        JSONArray ptsArr = new JSONArray();
        JSONObject json = new JSONObject();
        /*
        Save commands
         */
        for(int i = 0; i< Lists.alias.size() ; i++)
        {
            JSONObject obj = new JSONObject();

            obj.put("alias", Lists.alias.get(i));
            obj.put("command", Lists.commands.get(i));
            obj.put("value", Lists.values.get(i));

            comArr.add(obj);
        }
        json.put("commands", comArr);

        /*
        Save points
         */
        for(int i = 0; i< Lists.viewerALL.size() ; i++)
        {
            JSONObject obj = new JSONObject();

            obj.put("user", Lists.viewerALL.get(i));
            obj.put("points", Lists.viewerPoints.get(i));
            obj.put("watchtime", Lists.watchtime.get(i));

            ptsArr.add(obj);
        }
        //user.put("points", ptsArr);
        json.put("points", ptsArr);
        String userData = json.toJSONString();

        save(userData, Lists.channel + ".Json");
        System.out.println(userData);
        System.out.println(Lists.line);
    }
}

