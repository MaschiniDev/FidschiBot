import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class jsonWriter {
    public static void main(String[] args) {

        System.out.println("Save Data");
        JSONObject obj = new JSONObject();

        Iterator i = jsonReader.valueL.iterator();
        while (i.hasNext()) {
            String alias;
            String command;
            Long value;

        }




        obj.put("vac", jsonReader.vac);
        obj.put("reis", jsonReader.reis);
        obj.put("vegan", jsonReader.vegan);
        obj.put("porno", jsonReader.porno);
        obj.put("fail", jsonReader.fail);

        try {
            FileWriter file = new FileWriter("counts.json");
            file.write(obj.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Data: " + obj + "\nData Saved");
    }

    //public static ArrayList<JSONObject> commandvalues (ArrayList<ArrayList<String>> values) throws Exception {
    //    ArrayList<JSONObject> array = new ArrayList<>();
    //    values.forEach(array.add(new JSONObject() {{
    //        put("alias", alias);
    //        put("command", command);
    //        put("value", values);
//
//
    //    }}));
    //}
}