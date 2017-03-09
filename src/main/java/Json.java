import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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

    static void Reader (){
        System.out.println("Start jsonReader.Main");
        String sURL = "http://tmi.twitch.tv/group/user/" + Lists.channel + "/chatters";
        String file = Lists.channel + ".Json" /*"maschini.Json"*/;
        JSONParser parser = new JSONParser();

        try {
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
        } catch (IOException ioe){
            System.out.println(ioe);
        } finally {
            try {
                Object obj = parser.parse(readUrl(sURL));
                JSONObject jsonObject = (JSONObject) obj;

                String viewerCount = ((JSONObject) obj).get("chatter_count").toString();
                System.out.println(viewerCount);

                JSONObject chatters = (JSONObject) jsonObject.get("chatters");
                JSONArray vArr = (JSONArray) chatters.get("viewers");
                System.out.println("Viewer: " + vArr);
                for (int i = 0; i < vArr.size(); i++) {
                    String user = vArr.get(i).toString();
                    Lists.viewerLive.add(user);
                    if (!Lists.viewerALL.contains(user)) {
                        Lists.viewerALL.add(user);
                        Lists.viewerPoints.add(0);
                        Lists.watchtime.add(0);
                    }
                }
                /*
                Add Mods to Lists (old: Create Blacklist)
                 */
                JSONArray mArr = (JSONArray) chatters.get("moderators");
                System.out.println("Mods: " + mArr);
                Lists.mods.add(Lists.channel); Lists.mods.add("maschini");
                for (int i = 0; i < mArr.size(); i++) {
                    String mods = mArr.get(i).toString();
                    Lists.mods.add(mods);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            File f = new File(file);
            if(!f.exists()) {
                try {
                    FileWriter fW = new FileWriter(Lists.channel + ".Json" /*"maschini.Json"*/);
                    fW.write("{\"commands\":[],\"points\":[]}");
                    fW.flush();
                    fW.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } finally {
                    System.out.println("New File written");
                }
            }
            System.out.println(Lists.channel);

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
            JSONArray points = (JSONArray) jsonObject.get("points");
            if (points != null) {
                for (int i = 0; i < points.size(); i++) {
                    JSONObject point = (JSONObject) points.get(i);

                    Lists.viewerALL.add((String) point.get("user"));
                    Lists.viewerPoints.add(((Long) point.get("points")).intValue());
                    Lists.watchtime.add(((Long) point.get("watchtime")).intValue());
                }
            }
            System.out.println(Lists.viewerPoints);
            System.out.println(Lists.viewerALL);
            System.out.println("live: " + Lists.viewerLive);

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

