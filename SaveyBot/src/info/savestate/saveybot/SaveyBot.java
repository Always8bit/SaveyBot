package info.savestate.saveybot;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import org.jibble.pircbot.*;

/**
 * A message saving/recalling database IRC bot.
 * @author Joseph El-Khouri
 */
public class SaveyBot extends PircBot {
    
    private final ConfigReader configuration;
    private final JSONFileManipulator jfm;
    private final ArrayList<CircleTimer> floodTimers;
    private final FloodProtection fp;
    
    public SaveyBot(String configPath, String dbPath, String logPath) throws IOException {
        configuration = new ConfigReader(configPath);
        jfm = new JSONFileManipulator(dbPath, logPath);
        floodTimers = new ArrayList<>();
        
        setVerbose(true);
        setMessageDelay(Integer.parseInt(configuration.getParam("MS_DELAY")[0]));
        setEncoding("UTF-8");
        setName (configuration.getParam("NAME")[0]);
        setLogin(configuration.getParam("NAME")[0]);
        
        int floodSize = Integer.parseInt(configuration.getParam("FLOOD")[0]);
        int threshold = Integer.parseInt(configuration.getParam("FLOOD")[1]) * 60;
        int leaveFor  = Integer.parseInt(configuration.getParam("FLOOD")[2]) * 60 ;
        
        try {
            connect(configuration.getParam("SERVER")[0]);
        } catch (IrcException ex) {
            System.err.println("Could not connect to IRC Server!");
        }
        
        for(String s : configuration.getParam("+CHAN")) {
            joinChannel(s);
        }
        for(String s : configuration.getParam("CHAN")) {
            floodTimers.add(new CircleTimer(floodSize, threshold, leaveFor, s));
            joinChannel(s);
        }
        
        String[] login = configuration.getParam("LOGIN");
        if (login != null) {
            if (login.length == 4) {
                this.sendMessage(login[0], login[1] + " " 
                                         + login[2] + " " 
                                         + login[3]);
            }
        }
        
        fp = new FloodProtection(this);
    }
    
    public ArrayList<CircleTimer> getFloodTimers() {
        return floodTimers;
    }

    @Override
    public void onMessage(String channel, String sender,
                           String login, String hostname, String message) {
        
        boolean verbose = true;
        for (String s : configuration.getParam("CHAN")) {
            if (s.equalsIgnoreCase(channel))
                verbose = false;
        }
        
        if (message.toLowerCase().startsWith("!this")) {
            String THISMessage = message.toLowerCase().trim();
            // special !this checking pre-command parsing
            final String DEFAULT_PENIS = "8===============D";            
            if (THISMessage.equals("!this")) { 
                sendMessage(channel, DEFAULT_PENIS); 
                for (CircleTimer ct : floodTimers) {
                    if (channel.equals(ct.getChannel())) {
                        ct.tick();
                    } 
                }
                return;
            }
            BigInteger size;
            int actualSize = 0;
            try {
                String sizeParam = THISMessage.split("\\s+", 2)[1];
                size = new BigInteger(sizeParam);
                if (size.compareTo(new BigInteger("-2000")) == -1) {
                    actualSize = -2000;
                } else if (size.compareTo(new BigInteger("2000")) == 1) {
                    actualSize = 2000;
                } else {
                    actualSize = Integer.parseInt(sizeParam);
                }
            } catch (Exception e) {
                sendMessage(channel, DEFAULT_PENIS); 
                for (CircleTimer ct : floodTimers) {
                    if (channel.equals(ct.getChannel())) {
                        ct.tick();
                    } 
                }
                return;
            }
            StringBuilder sb = new StringBuilder();
            char begin;
            char end;
            if (actualSize < 0) {
                begin = 'D';
                end = '8';
            } else {
                begin = '8';
                end = 'D';
            }
            sb.append(begin);
            actualSize = Math.abs(actualSize);
            for (int i=0; i<actualSize; i++) sb.append('=');
            sb.append(end);
            sendMessage(channel, sb.toString());
            for (CircleTimer ct : floodTimers) {
                if (channel.equals(ct.getChannel())) {
                    ct.tick();
                } 
            }
            return;
        }
        
        String[] command = parseCommand(message);
        if (command != null) {
            if (command.length > 0) {
                if (command[0].length() > 0) {
                    if (command[0].charAt(0) == '!')
                        // dumb !this checking poop crap
                        verbose = false;
                }
            }
            
            String parsed = CommandParse.parseCommand(command, verbose, jfm, sender);
            if (parsed == null) return;
            for (CircleTimer ct : floodTimers) {
                if (channel.equals(ct.getChannel())) {
                    ct.tick();
                } 
            }
            String prepend = "~ ";
            int msgMaxLength = 448 - configuration.getParam("NAME")[0].length() - prepend.length();
            if (parsed.length() < msgMaxLength || !verbose) {
                sendMessage(channel, prepend.concat(parsed));
            } else {
                int offset = 0;
                while(true) {
                    try {
                        sendMessage(channel, prepend.concat(
                                                parsed.substring(offset, offset+msgMaxLength)
                                             ));
                        offset += msgMaxLength;
                    } catch (Exception e) {
                        sendMessage(channel, prepend.concat(parsed.substring(offset)));
                        break;
                    }
                }
            }
        }
    }
    
    private String[] parseCommand(String text) {
        for (String s : configuration.getParam("INVOKING")) {
            if (!(s.charAt(0) == text.charAt(0)))
                continue;
            text = text.trim();
            String[] splitCommand = text.split("\\s+", 2);
            if (splitCommand[0].length() <= 1) return null;
            splitCommand[0] = splitCommand[0].substring(1);
            return splitCommand;
        }
        if ('!' == text.charAt(0)) {
            text = text.trim();
            String[] splitCommand = text.split("\\s+", 2);
            if (splitCommand[0].length() <= 1) return null;
            return splitCommand;
        }
        return null;
    }
    
    public ConfigReader getConfig() {
        return configuration;
    }
}
