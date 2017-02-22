import java.util.ArrayList;
import java.util.List;

public class list {
    //stuff
    static String timeStamp;
    static String channel = "maschini";
    static String line = "------------------------------------------------------ \n";
    static Boolean write = true;

    /*
    //new Object lists
    static ArrayList<Object> commandList = new ArrayList<Object>();
    static ArrayList<Object> userList = new ArrayList<Object>();
    */

    //command data
    static List<String> alias = new ArrayList<String>();
    static List<String> commands = new ArrayList<String>();
    static List<Integer> values = new ArrayList<Integer>();

    //user data
    static List<String> viewerALL = new ArrayList<String>();
    static List<String> viewerLive = new ArrayList<String>();
    static List<Integer> viewerPoints = new ArrayList<Integer>();
    static List<Integer> watchtime = new ArrayList<Integer>();

    //moderater list -> Für admin funktionen
    static ArrayList<String> mods = new ArrayList<String>();
}
