import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        while(true) {
            long deltaT = System.currentTimeMillis();
            boolean status = checkForInternet();
            deltaT = System.currentTimeMillis() - deltaT;
            statusAppender(deltaT);
            System.out.println(getCurrentDateTimeString() + " " + deltaT);
            waiting(5000 - deltaT);
        }
    }

    public static boolean checkForInternet() {
        try {
            URL url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(4500);
            connection.connect();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static void waiting(long delay) {
        try {
            Thread.sleep(Math.max(delay,0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void statusAppender(long ping) {
        //String statusString = connectionStatus == true ? "1" : "0";

        String textToAppend = getCurrentDateTimeString() + "," + ping;
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("log.txt", true)  //Set true for append mode
            );
            writer.write(textToAppend);
            writer.newLine();   //Add new line
            writer.close();
        } catch (Exception e) {
            System.out.println("Unable to write to file!");
        }
    }

    public static String getCurrentDateTimeString() {
        LocalDateTime myObj = LocalDateTime.now();
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(myObj);
    }

}
