import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    //Log method -> Protokolliert Chat in Console und Log.txt
    static String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    static BufferedWriter logW() throws IOException {
        BufferedWriter bf = new BufferedWriter(new FileWriter("Log/" + date + ".txt"));
        return bf;
    }

    static void write(String text) {
        if (Lists.write)
            System.out.println(Lists.timeStamp + text);
        try {
            logW().write(Lists.timeStamp + text + "\n");
            logW().flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
