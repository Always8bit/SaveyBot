package info.savestate.saveybot;

import java.io.IOException;
import org.jibble.pircbot.*;

/**
 * A message saving/recalling database IRC bot.
 * @author Joseph El-Khouri
 */
public class SaveyBot extends PircBot {
    
    private final ConfigReader configuration;
    private final JSONFileManipulator jfm;
    
    public SaveyBot(String configPath, String dbPath) throws IOException {
        configuration = new ConfigReader(configPath);
        jfm = new JSONFileManipulator(dbPath);
        setVerbose(true);
        setName (configuration.getParam("NAME")[0]);
        setLogin(configuration.getParam("NAME")[0]);
        try {
            connect(configuration.getParam("SERVER")[0]);
        } catch (IrcException ex) {
            System.err.println("Could not connect to IRC Server!");
        }
        for(String s : configuration.getParam("+CHAN")) {
            joinChannel(s);
        }
        
        /*
        for(String s : configuration.getParam("CHAN")) {
            joinChannel(s);
        }
        */
    }

    @Override
    public void onMessage(String channel, String sender,
                           String login, String hostname, String message) {
        
        boolean verbose = true;
        for (String s : configuration.getParam("CHAN")) {
            if (s.equalsIgnoreCase(channel))
                verbose = false;
        }
        
        String[] command = parseCommand(message);
        if (command != null) {
            String parsed = CommandParse.parseCommand(command, verbose, jfm);
            if (parsed != null) sendMessage(channel, "~ " + parsed);
        }
    }
    
    private String[] parseCommand(String text) {
        boolean invoke = false;
        for (String s : configuration.getParam("INVOKING")) {
            if (s.charAt(0) == text.charAt(0))
                invoke = true;
        }
        if (!invoke) return null;
        text = text.trim();
        String[] splitCommand = text.split("\\s+", 2);
        if (splitCommand[0].length() <= 1) return null;
        splitCommand[0] = splitCommand[0].substring(1);
        return splitCommand;
    }
    
    public ConfigReader getConfig() {
        return configuration;
    }
}
