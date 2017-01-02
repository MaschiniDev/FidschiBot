import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class jsonWriter {

    public static void main(String[] args) {
        System.out.println("Save Data");
        System.out.println("00%");
        JSONArray arr = new JSONArray();
        JSONObject commands = new JSONObject();
        for(int i = 0 ; i< main.aliasL.size() ; i++)
        {
            JSONObject obj = new JSONObject();

            String alias = main.aliasL.get(i);
            String command = main.commandL.get(i);
            Long value = main.valueL.get(i);

            obj.put("alias", alias);
            obj.put("command", command);
            obj.put("value", value);

            arr.add(obj);
        }
        commands.put("commands", arr);
        System.out.println("50%");

        String objS = commands.toJSONString();

        try {
            FileWriter file = new FileWriter("data.json");
            file.write(objS);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("100%");
        System.out.println(main.line);
    }


}

