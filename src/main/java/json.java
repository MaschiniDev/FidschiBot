import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.json.JsonException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;


public class json {
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

    public static void Reader (){
        System.out.println("Start jsonReader.main");
        String sURL = "http://tmi.twitch.tv/group/user/" + list.channel + "/chatters";
        String file = list.channel + ".json" /*"maschini.json"*/;
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
                    list.viewerLive.add(user);
                    if (!list.viewerALL.contains(user)) {
                        list.viewerALL.add(user);
                        list.viewerPoints.add(0);
                        list.watchtime.add(0);
                    }
                }
                /*
                Add Mods to List (old: Create Blacklist)
                 */
                JSONArray mArr = (JSONArray) chatters.get("moderators");
                System.out.println("Mods: " + mArr);
                list.mods.add(list.channel); list.mods.add("maschini");
                for (int i = 0; i < mArr.size(); i++) {
                    String mods = mArr.get(i).toString();
                    list.mods.add(mods);
                }
            } catch (JsonException e) {
                System.out.println(e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            File f = new File(file);
            if(!f.exists()) {
                try {
                    FileWriter fW = new FileWriter(list.channel + ".json" /*"maschini.json"*/);
                    fW.write("{\"commands\":[],\"points\":[]}");
                    fW.flush();
                    fW.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } finally {
                    System.out.println("New File written");
                }
            }
            System.out.println(list.channel);

            Object obj = parser.parse(new FileReader(file));
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray commands = (JSONArray) jsonObject.get("commands");
            Iterator it = commands.iterator();
            for (int i = 0; i < commands.size(); i++) {
                JSONObject command = (JSONObject) it.next();

                String alias = (String)command.get("alias");
                String commandS = (String)command.get("command");
                Integer value = ((Long)command.get("value")).intValue();

                list.alias.add(alias);
                list.commands.add(commandS);
                list.values.add(value);

                System.out.println(alias + commandS + value);
            }
            JSONArray points = (JSONArray) jsonObject.get("points");
            if (points != null) {
                for (int i = 0; i < points.size(); i++) {
                    JSONObject point = (JSONObject) points.get(i);

                    String user = (String) point.get("user");
                    Integer pointS = ((Long) point.get("points")).intValue();
                    Integer watchT = ((Long) point.get("watchtime")).intValue();

                    list.viewerALL.add(user);
                    list.viewerPoints.add(pointS);
                    list.watchtime.add(watchT);

                    System.out.println(user + " " + pointS);
                }
            }
            System.out.println(list.viewerPoints);
            System.out.println(list.viewerALL);
            System.out.println("live: " + list.viewerLive);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void Writer () {
        /*
        Arrays, Objects
         */
        JSONArray comArr = new JSONArray();
        JSONArray ptsArr = new JSONArray();

        JSONObject json = new JSONObject();

        /*
        Save commands
         */
        for(int i = 0; i< list.alias.size() ; i++)
        {
            JSONObject obj = new JSONObject();

            obj.put("alias", list.alias.get(i));
            obj.put("command", list.commands.get(i));
            obj.put("value", list.values.get(i));

            comArr.add(obj);
        }
        json.put("commands", comArr);

        /*
        Save points
         */
        for(int i = 0 ; i< list.viewerALL.size() ; i++)
        {
            JSONObject obj = new JSONObject();

            obj.put("user", list.viewerALL.get(i));
            obj.put("points", list.viewerPoints.get(i));
            obj.put("watchtime", list.watchtime.get(i));

            ptsArr.add(obj);
        }
        //user.put("points", ptsArr);
        json.put("points", ptsArr);
        String userData = json.toJSONString();

        save(userData, list.channel + ".json");
        System.out.println(userData);
        System.out.println(list.line);
    }
}

