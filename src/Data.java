import java.util.HashMap;
import java.util.List;

public class Data {
    static String line = "------------------------------------------------------ \n";

    static int live = 0;
    static List viewer = null;
    static HashMap<String, HashMap<String, Integer>> user = new HashMap<String, HashMap<String, Integer>>();

    static HashMap<String, String> commandMessage = new HashMap<String, String>();
    static HashMap<String, Integer> commandValues = new HashMap<String, Integer>();
}
