/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.savestate.saveybot;

import java.math.BigInteger;

/**
 *
 * @author Joseph El-Khouri
 */
public class CommandParse {
    
    public static String parseCommand(String[] command, boolean verbose, JSONFileManipulator jfm, String username) {
        
        String invoke = command[0].toLowerCase();
        
        if (invoke.equals("load")  && (command.length > 1)) {
            String param = getParam(command);
            if (param != null) 
                return jfm.getSlot(param, verbose);
        }
        
        if (invoke.equals("save") && (getParam(command) != null)) {
            // test to see if it's a big integer
            String param = getParam(command);
            BigInteger slot = null;
            try {
                slot = new BigInteger(param);
            } catch (Exception e) {
                System.err.println("Not specificing a slot, saving to lowest free state...");
            }
            if (slot == null) {
                return jfm.saveSlot(username, command[1]);
            } else {
                return jfm.saveSlot(slot.toString(), username, getTextExcludingParam(command));
            }
        }
        
        if (invoke.equals("help"))
            return "lol u n00b (^: http://savestate.info/upload/IRCHelp.png";

        if (invoke.equals("markov") || invoke.equals("markof")) {
            if (command.length <= 1)
                return jfm.markOf();
            String param = getParam(command);
            if (param != null)
                return jfm.markOf(param);
        }
        
        if (invoke.equals("remove")) {
            String param = getParam(command);
            return jfm.remove(param, username);
        }
        
        if (invoke.equals("road")) {
            if (command.length <= 1)
                return jfm.randomLoad();
            String param = getParam(command);
            if (param != null)
                return jfm.randomLoad(param);
            return "lmao tell savestate i broke (this is an error)";
        }

        return null;
    }
    
    private static String getParam(String[] command) {
        if (command.length < 2)
            return null;
        if (command[1].isEmpty())
            return null;
        String[] words = command[1].split("\\s+");
        if (words.length == 0)
            return null;
        if (words[0].isEmpty())
            return null;
        return words[0];
    }
    
    private static String getTextExcludingParam(String[] command) {
        if (command.length < 2)
            return null;
        if (command[1].isEmpty())
            return null;
        String[] words = command[1].split("\\s+", 2);
        if (words.length < 2)
            return null;
        if (words[1].isEmpty())
            return null;
        return words[1];
    }
    
}
