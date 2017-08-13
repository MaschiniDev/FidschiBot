import java.util.ArrayList;
import java.util.List;

public class Lists {
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
    static java.util.List<String> alias = new ArrayList<String>();
    static java.util.List<String> commands = new ArrayList<String>();
    static java.util.List<Integer> values = new ArrayList<Integer>();

    //user data
    static java.util.List<String> viewerALL = new ArrayList<String>();
    static java.util.List<String> viewerLive = new ArrayList<String>();
    static java.util.List<Integer> viewerPoints = new ArrayList<Integer>();
    static java.util.List<Integer> watchtime = new ArrayList<Integer>();

    //moderater Lists -> Für admin funktionen
    static ArrayList<String> mods = new ArrayList<String>();
}
