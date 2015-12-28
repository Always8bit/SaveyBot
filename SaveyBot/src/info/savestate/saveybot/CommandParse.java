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
            String[] params = command[1].trim().split("\\s+");
            if (params.length >= 1) 
                return jfm.getSlot(params[0], verbose);
        }
        
        if (invoke.equals("save") && !(command[1].isEmpty())) {
            // test to see if it's a big integer
            String[] params = command[1].trim().split("\\s+", 2);
            BigInteger slot = null;
            try {
                slot = new BigInteger(params[0]);
            } catch (Exception e) {
                System.err.println("Not specificing a slot, saving to lowest free state...");
            }
            if (slot == null) {
                return jfm.saveSlot(username, command[1]);
            } else {
                return jfm.saveSlot(slot.toString(), username, params[1]);
            }
        }
        
        if (invoke.equals("road")) {
            if (command.length <= 1)
                return jfm.randomLoad();
            String[] params = command[1].trim().split("\\s+", 2);
            if (!params[0].isEmpty())
                return jfm.randomLoad(params[0]);
            return "lmao tell savestate i broke (this is an error)";
        }

        return null;
    }
    
}
