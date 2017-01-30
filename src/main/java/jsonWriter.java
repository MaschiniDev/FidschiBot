import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class jsonWriter {

    //save to file method
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

    //save data method
    public static void main(String[] args) {
        /*
        Arrays, Objects
         */
        JSONArray comArr = new JSONArray();
        JSONArray ptsArr = new JSONArray();

        JSONObject json = new JSONObject();

        /*
        Save commands
         */
        for(int i = 0 ; i< main.aliasL.size() ; i++)
        {
            JSONObject obj = new JSONObject();

            obj.put("alias", main.aliasL.get(i));
            obj.put("command", main.commandL.get(i));
            obj.put("value", main.valueL.get(i));

            comArr.add(obj);
        }
        json.put("commands", comArr);

        /*
        Save points
         */
        for(int i = 0 ; i< main.viewerALL.size() ; i++)
        {
            JSONObject obj = new JSONObject();

            obj.put("user", main.viewerALL.get(i));
            obj.put("points", main.viewerPoints.get(i));
            obj.put("watchtime", /*main.watchtime.get(i)*/ 0);

            ptsArr.add(obj);
        }
        //user.put("points", ptsArr);
        json.put("points", ptsArr);
        String userData = json.toJSONString();

        save(userData, main.channel + ".json");
        System.out.println(userData);
        System.out.println(main.line);
    }
}

