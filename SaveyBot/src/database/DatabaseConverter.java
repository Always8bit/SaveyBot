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
    
    public DatabaseConverter(String legacyDatabaseFilename) throws IOException {
        ready = false;
        File file = new File(legacyDatabaseFilename);
        if(!(file.exists() && !file.isDirectory())) 
            throw new IOException("Provided database " + legacyDatabaseFilename + " does not exist!");
        this.legacyDatabaseFilename = legacyDatabaseFilename;
    }
    
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
    
    public void toJSON() {
        if (!ready) {
            System.out.println("ERROR: Database not ready to be converted");
            System.out.println("Did you importDatabase(); yet?");
            return;
        }
    }
}
