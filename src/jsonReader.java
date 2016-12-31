import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class jsonReader {

    public static long vac;
    public static long porno;
    public static long vegan;
    public static long reis;
    public static long fail;

    static List<String> aliaseL = new ArrayList<>();
    static List<String> commandL = new ArrayList<>();
    static List<Long> valueL = new ArrayList<>();

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            System.out.println("Read Command Data");
            Object obj = parser.parse(new FileReader("data.json"));
            //Object obj = parser.parse(new FileReader("counts.json"));
            JSONObject jsonObject = (JSONObject) obj;


            JSONArray commandscont = (JSONArray) jsonObject.get("commands");
            Iterator i = commandscont.iterator();
            while (i.hasNext()) {
                JSONObject commnd = (JSONObject) i.next();

                String alias;
                String command;
                long value;

                alias = (String)commnd.get("alias");
                command = (String)commnd.get("command");
                value = (long)commnd.get("value");

                aliaseL.add(alias);
                commandL.add(command);
                valueL.add(value);
            }
            System.out.println("aliase: " + aliaseL);
            System.out.println("commands: " + commandL);
            System.out.println("values: " + valueL);

//
            //vac = (long) jsonObject.get("vac");
            //porno = (long) jsonObject.get("porno");
            //reis = (long) jsonObject.get("reis");
            //vegan = (long) jsonObject.get("vegan");
            //fail = (long) jsonObject.get("fail");
//
            //System.out.println("!vac: " + vac);
            //System.out.println("!porno: " + porno);
            //System.out.println("!reis: " + reis);
            //System.out.println("!vegan: " + vegan);
            //System.out.println("!fail: " + fail);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}