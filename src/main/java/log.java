import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class log {
    //log method -> Protokolliert Chat in Console und log.txt
    static String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    static BufferedWriter logW() throws IOException {
        BufferedWriter bf = new BufferedWriter(new FileWriter("log/" + date + ".txt"));
        return bf;
    }

    static void write(String text) {
        if (list.write)
            System.out.println(list.timeStamp + text);
        try {
            logW().write(list.timeStamp + text + "\n");
            logW().flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
