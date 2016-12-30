import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

public class jsonWriter {
    public static void main(String[] args) {

        System.out.println("Save Command Data");
        JSONObject obj = new JSONObject();
        obj.put("vac", jsonReader.vac);
        obj.put("reis", jsonReader.reis);
        obj.put("vegan", jsonReader.vegan);
        obj.put("porno", jsonReader.porno);

        try {
            FileWriter file = new FileWriter("counts.json");
            file.write(obj.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(obj + "");
    }
}