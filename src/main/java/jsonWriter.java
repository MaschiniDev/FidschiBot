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
        Save DATA.JSON
         */
        JSONArray arr = new JSONArray();
        JSONObject commands = new JSONObject();
        for(int i = 0 ; i< main.aliasL.size() ; i++)
        {
            JSONObject obj = new JSONObject();

            String alias = main.aliasL.get(i);
            String command = main.commandL.get(i);
            Integer value = main.valueL.get(i);

            obj.put("alias", alias);
            obj.put("command", command);
            obj.put("value", value);

            arr.add(obj);
        }
        commands.put("commands", arr);
        String commandData = commands.toJSONString();
        save(commandData, "data.json");

        /*
        Save USER.JSON
         */
        JSONArray arra = new JSONArray();
        JSONObject user = new JSONObject();
        for(int i = 0 ; i< main.viewerALL.size() ; i++)
        {
            JSONObject obj = new JSONObject();

            String User = main.viewerALL.get(i);
            Integer Points = main.viewerPoints.get(i);

            obj.put("user", User);
            obj.put("points", Points);

            arra.add(obj);
        }
        user.put("users", arra);
        String userData = user.toJSONString();
        save(userData, "user.json");
        System.out.println("100%");

        System.out.println(main.line);
    }


}

