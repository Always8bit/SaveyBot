
// !!!!! READ THIS !!!!!
// NETBEANS LIKES TO IGNORE LIBRARIES IN THE DEFAULT PACKAGE
// So I had to copy NameContainer.class to the source folder as well as
// import it as a library.

// That is all. 

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A conversion utility for converting the old database type to JSON. 
 * The previous database was stored as a serialized ArrayList&lt;NameContainer&gt;. 
 * This takes in the old database and creates a .json file equivalent to the 
 * provided legacy database. 
 * @author Joseph El-Khouri
 */
public class DatabaseConverter {
    
    private String legacyDatabaseFilename;
    private ArrayList<NameContainer> database;
    private boolean ready;
    private JSONArray databaseJSON;
    
    /**
     * Constructs a new DatabaseConverter, checking to see if the file provided exists.
     * @param legacyDatabaseFilename The filepath to the legacy database.
     * @throws IOException If the file does not exist.
     */
    public DatabaseConverter(String legacyDatabaseFilename) throws IOException {
        database     = null;
        databaseJSON = null;
        ready = false;
        File file = new File(legacyDatabaseFilename);
        if(!(file.exists() && !file.isDirectory())) 
            throw new IOException("Provided database " + legacyDatabaseFilename + " does not exist!");
        this.legacyDatabaseFilename = legacyDatabaseFilename;
    }
    
    /**
     * Prepares the database for conversion to JSON text. This must be run
     * before calling toJSON(). 
     */
    public void importDatabase() {
        try (
                FileInputStream fis = new FileInputStream(legacyDatabaseFilename); 
                ObjectInputStream ois = new ObjectInputStream(fis)
            ) {
            database = (ArrayList)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR: Database not imported.");
            System.out.println("       FILE: " + legacyDatabaseFilename);
            System.out.println("       Was the provided file a legacy database?");
            e.printStackTrace();
            return;
            
        }
        ready = true;
    }
    
    /**
     * Converts the legacy database into JSON format. Can only be run after
     * importDatabase() is called. 
     */
    public void toJSON() {
        if (!ready) {
            System.out.println("ERROR: Database not ready to be converted");
            System.out.println("       Did you importDatabase(); yet?");
            return;
        }
        databaseJSON = new JSONArray();
        for (NameContainer dbEntry : database) {
            JSONObject jsonEntry = new JSONObject();
            jsonEntry.put("slot", dbEntry.getSlot().toString().trim());
            jsonEntry.put("name", dbEntry.getName().trim());
            jsonEntry.put("message", dbEntry.getData().trim());
            databaseJSON.put(jsonEntry);
        }
    }
    
    @Override
    public String toString() {
        if (databaseJSON == null)
            return "Null JSON Database";
        return databaseJSON.toString(1);
    }
}
