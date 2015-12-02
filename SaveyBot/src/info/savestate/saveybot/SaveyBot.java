package info.savestate.saveybot;

import java.io.IOException;

/**
 * A message saving/recalling database IRC bot.
 * @author Joseph El-Khouri
 */
public class SaveyBot {
    
    private ConfigReader configuration;
    
    public SaveyBot(String configPath) throws IOException {
        configuration = new ConfigReader(configPath);
    }
    
    public ConfigReader getConfig() {
        return configuration;
    }
}
