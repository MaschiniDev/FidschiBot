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
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
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
        String sURL = "http://tmi.twitch.tv/group/user/" + main.channel + "/chatters";
        String file = main.channel + ".json";

        URL url = new URL(sURL);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

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
            //JSONArray mArr = (JSONArray) chatters.get("moderators");
            //System.out.println("Mods: " + mArr);
            //for (int i = 0; i < mArr.size(); i++) {
            //    String blacklist = mArr.get(i).toString();
            //    main.blacklist.add(blacklist);
            //}
        } catch (JsonException e) {
            e.printStackTrace();
        }
        try {
            File f = new File(file);
            if(!f.exists()) {
                f.createNewFile();
                try {
                    FileWriter fW = new FileWriter(main.channel + ".json");
                    fW.write("{\"commands\":[],\"points\":[]}");
                    fW.flush();
                    fW.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
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

                    main.viewerALL.add(user);
                    main.viewerPoints.add(pointS);

                    System.out.println(user + " " + pointS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}