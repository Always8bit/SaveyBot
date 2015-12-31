
import info.savestate.saveybot.*;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The Main Class for SaveyBot.
 * @author Joseph El-Khouri
 */
public class Main {

    /**
     * @param a The command line arguments.
     * @throws java.io.IOException thrown in provided inputs aren't found.
     */
    public static void main(String[] a) throws IOException {
        String configPath = "saveybot.cfg";
        String dbPath = "saveybot.json";
        String logPath = "log.html";
        for (int i=0; i<a.length; i++) {
            // decode json 
            if (a[i].equals("--json")) {
                databaseToJSON(a[++i]);
                return;
            }
            if (a[i].equals("--config"))
                configPath = a[++i];
            if (a[i].equals("--db"))
                dbPath = a[++i];
            if (a[i].equals("--log"))
                logPath = a[++i];
        }
        try {
            SaveyBot sb = new SaveyBot(configPath, dbPath, logPath);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("\nUsage: SaveyBot.jar [--json] [--config PATH]\n"); 
            sb.append("                    [--db PATH] [--log PATH]\n");
            sb.append("\n");
            sb.append("Options:\n");
            sb.append("    --json         Activates the old database -> json\n"
                    + "                   conversion tool. Shouldn't be of\n"
                    + "                   any use to anyone but Savestate...\n"
                    + "  --config PATH    The location of the configure file.\n"
                    + "                   If not specified, defaults to\n"
                    + "                   \"saveybot.cfg\"\n"
                    + "      --db PATH    The location of the database. If\n"
                    + "                   not specified, defaults to \n"
                    + "                   \"saveybot.db\"\n"
                    + "     --log PATH    The location where the HTML log\n"
                    + "                   should be saved. If not specified,\n"
                    + "                   defaults to \"log.html\"\n");
            sb.append("\n"
                    + " SaveyBot designed by Savestate\n"
                    + " --> https://github.com/Always8bit/SaveyBot/");
            System.out.println(sb.toString());
        }
    }
    
    /**
     * Converts an ArrayList&lt;NameContainer&gt; serialized object to JSON.
     * Uses the DatabaseConverter along with a legacy class file called NameContainer.
     * @param filepath The path to the legacy database file.
     */
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
