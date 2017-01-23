import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.json.JsonException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class jsonReader {

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

    public static void main(String[] args) throws Exception {
        System.out.println("Start jsonReader.main");
        String sURL = "http://tmi.twitch.tv/group/user/" + main.channel + "/chatters";
        String file = main.channel + ".json" /*"maschini.json"*/;

        try {
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
        } catch (IOException ioe){
            System.out.println(ioe);
            jsonReader.main(args);
        }

        JSONParser parser = new JSONParser();
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
                main.viewerLive.add(user);
            }
            /*
            Add Mods to Live (old: Create Blacklist)
             */
            JSONArray mArr = (JSONArray) chatters.get("moderators");
            System.out.println("Mods: " + mArr);
            for (int i = 0; i < mArr.size(); i++) {
                String mods = mArr.get(i).toString();
                main.viewerLive.add(mods);
            }
        } catch (JsonException e) {
            e.printStackTrace();
        }
        try {
            File f = new File(file);
            if(!f.exists()) {
                try {
                    FileWriter fW = new FileWriter(main.channel + ".json" /*"maschini.json"*/);
                    fW.write("{\"commands\":[],\"points\":[]}");
                    fW.flush();
                    fW.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } finally {
                    System.out.println("New File written");
                }
            }
            System.out.println(main.channel);

            Object obj = parser.parse(new FileReader(file));
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray commands = (JSONArray) jsonObject.get("commands");
            Iterator it = commands.iterator();
            for (int i = 0; i < commands.size(); i++) {
                JSONObject command = (JSONObject) it.next();

                String alias = (String)command.get("alias");
                String commandS = (String)command.get("command");
                Integer value = ((Long)command.get("value")).intValue();

                main.aliasL.add(alias);
                main.commandL.add(commandS);
                main.valueL.add(value);

                System.out.println(alias + commandS + value);
            }
            JSONArray points = (JSONArray) jsonObject.get("points");
            if (points != null) {
                for (int i = 0; i < points.size(); i++) {
                    JSONObject point = (JSONObject) points.get(i);

                    String user = (String) point.get("user");
                    Integer pointS = ((Long) point.get("points")).intValue();
                    Integer watchT = ((Long) point.get("watchtime")).intValue();

                    main.viewerALL.add(user);
                    main.viewerPoints.add(pointS);
                    main.watchtime.add(watchT);

                    System.out.println(user + " " + pointS);
                }
            }
            System.out.println(main.viewerPoints);
            System.out.println(main.viewerALL);
            System.out.println("live: " + main.viewerLive);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}