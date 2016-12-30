import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class jsonReader {

    public static long vac;
    public static long porno;
    public static long vegan;
    public static long reis;

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            System.out.println("Read Command Data");
            Object obj = parser.parse(new FileReader("counts.json"));
            JSONObject jsonObject = (JSONObject) obj;

            vac = (long) jsonObject.get("vac");
            System.out.println("!vac: " + vac);
            porno = (long) jsonObject.get("porno");
            System.out.println("!porno: " + porno);
            reis = (long) jsonObject.get("reis");
            System.out.println("!reis: " + reis);
            vegan = (long) jsonObject.get("vegan");
            System.out.println("!vegan: " + vegan);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}