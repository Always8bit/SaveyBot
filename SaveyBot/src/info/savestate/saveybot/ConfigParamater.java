package info.savestate.saveybot;

/**
 * A simple class for organizing configuration data.
 * @author Joseph El-Khouri
 */
public class ConfigParamater {
    
    private final String   name;
    private final String[] values;
    
    ConfigParamater(String name, String[] values) {
        this.name   = name;
        this.values = values;
    }
    
    public String getName() {
        return name;
    }
    
    public String[] getValues() {
        return values;
    }
    
}
