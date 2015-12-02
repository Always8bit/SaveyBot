
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The Main Class for SaveyBot.
 * @author Joseph El-Khouri
 */
public class SaveyBot {

    /**
     * @param a The command line arguments.
     */
    public static void main(String[] a) {
        for (int i=0; i<a.length; i++) {
            // decode json 
            if (a[i].equals("--json"))
                databaseToJSON(a[i+1]);
        }
    }
    
    public static void databaseToJSON(String filepath) {
        try {
            DatabaseConverter dbc = new DatabaseConverter(filepath);
            dbc.importDatabase();
            dbc.toJSON();
            String jsonString = dbc.toString();
            byte[] json = jsonString.getBytes("UTF-8");
            FileOutputStream fos = new FileOutputStream("db.json");
            fos.write(json);
            fos.close();
            System.out.println("JSON database saved to \"db.json\".");
        } catch (IOException e) {
            System.out.println("JSON database error.");
        }
    }
    
}
