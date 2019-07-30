import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        while(true) {
            boolean status = checkForInternet();
            System.out.println(status);
            statusAppender(status);
            waiting(1000);
        }
    }

    public static boolean checkForInternet() {
        try {
            URL url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static void waiting(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void statusAppender(boolean connectionStatus) {
        //if(connectionStatus) return;
        LocalDateTime myObj = LocalDateTime.now();
        String statusString = connectionStatus == true ? "1" : "0";

        String textToAppend = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(myObj) + "," + statusString;
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

}
