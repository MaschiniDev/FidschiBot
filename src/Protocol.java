import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Protocol {

    private static Boolean writeInFile = true;
    private static Boolean writeInConsole = true;
    private static String logFile = "protocol.txt";

    static void start(String path, Boolean InFile, Boolean InConsole){
        logFile = path;
        writeInFile = InFile;
        writeInConsole = InConsole;
    }

    //TODO getting this shit working!
    private static BufferedWriter writer() throws Exception {
        return new BufferedWriter(new FileWriter(logFile));
    }

    static void write(String text) {
        try {
            String output = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss > ").format(new Date()) + text;

            if (writeInFile) {
                writer().write(output);
                writer().newLine();
                writer().flush();
            }
            if (writeInConsole) {
                System.out.println(output);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
