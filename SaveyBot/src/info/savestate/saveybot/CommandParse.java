/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.savestate.saveybot;

/**
 *
 * @author Joseph El-Khouri
 */
public class CommandParse {
    
    public static String parseCommand(String[] command, boolean verbose, JSONFileManipulator jfm) {
        
        String invoke = command[0].toLowerCase();
        
        if (invoke.equals("load")  && (command.length > 1)) {
            String[] params = command[1].trim().split("\\s+");
            if (params.length >= 1) 
                return jfm.getSlot(params[0], verbose);
        }
        
        /*
        if (invoke.equals("save") && !(command[1].isEmpty())) {
            String[] params = command[1].trim().split("\\s+", 2);
            return jfm.saveSlot();
        }
        */
        return null;
    }
    
}
