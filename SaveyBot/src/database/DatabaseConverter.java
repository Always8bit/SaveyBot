package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

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
    
    /**
     * Constructs a new DatabaseConverter, checking to see if the file provided exists.
     * @param legacyDatabaseFilename The filepath to the legacy database.
     * @throws IOException If the file does not exist.
     */
    public DatabaseConverter(String legacyDatabaseFilename) throws IOException {
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
                FileInputStream fileInputStream = new FileInputStream(legacyDatabaseFilename); 
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
            ) {
            database = (ArrayList)objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR: Database not imported.");
            System.out.println("Was the provided file a legacy database?");
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
            System.out.println("Did you importDatabase(); yet?");
            return;
        }
    }
}
