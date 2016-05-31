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
            if (slot == null || command[1].split("\\s+").length == 1) {
                return jfm.saveSlot(username, command[1]);
            } else {
                return jfm.saveSlot(slot.toString(), username, getTextExcludingParam(command));
            }
        }
        
        if (invoke.equals("help"))
            return "lol u n00b (^: http://overcocked.penis.systems/commands.html";

        if (invoke.equals("markov") || invoke.equals("markof")) {
            if (command.length <= 1)
                return jfm.markOf();
            String param = getParam(command);
            if (param != null)
                return jfm.markOf(param);
        }
        
        if (invoke.equals("remove")) {
            String param = getParam(command);
            if (param != null)
                return jfm.remove(param, username);
        }
        
        if (invoke.equals("whois")) {
            String param = getParam(command);
            if (param != null)
                return jfm.whois(param);
        }
        
        if (invoke.equals("low")) {
            return jfm.low();
        }
        
        if (invoke.equals("high")) {
            return "(2^32)^Integer.MAX_VALUE";
        }
        
        if (invoke.equals("search")) {
            if (command.length == 2)
                return jfm.search(command[1], verbose);
        }
        
        if (invoke.equals("chain")) {
            if (command.length == 1)
                return jfm.chainall(verbose);
            if (command.length == 2)
                return jfm.chain(command[1], verbose);
            return "lmao the command was parsed REALLY badly. (tell savestate)";
        }
        
        if (invoke.equals("size")) {
            return jfm.size();
        }
        
        if (invoke.equals("names")) {
            return jfm.nameList(verbose);
        }
        
        if (invoke.equals("log")) {
            return jfm.log();
        }
        
        if (invoke.equals("source")) {
            return "https://github.com/Always8bit/SaveyBot/";
        }
        
        if (invoke.equals("json")) {
            return "http://overcocked.penis.systems/saveybot.json";
        }
        
        if (invoke.equals("road")) {
            if (command.length <= 1)
                return jfm.randomLoad();
            String param = getParam(command);
            if (param != null)
                return jfm.randomLoad(param);
            return "lmao tell savestate i broke (this is an error)";
        }
        
        if (invoke.equals("!this")) {
            final String DEFAULT_PENIS = "8===============D";
            String param = getParam(command);
            if (param == null)
                return DEFAULT_PENIS;
            try {
                BigInteger bi = new BigInteger(param);
                if (bi.compareTo(new BigInteger("2000")) == 1)
                    param = "2000";
                if (bi.compareTo(new BigInteger("-2000")) == -1)
                    param = "-2000";
                int size = Integer.parseInt(param);
                StringBuilder sb = new StringBuilder();
                char begin;
                char end;
                if (size < 0) {
                    begin = 'D';
                    end = '8';
                } else {
                    begin = '8';
                    end = 'D';
                }
                sb.append(begin);
                size = Math.abs(size);
                for (int i=0; i<size; i++) sb.append('=');
                sb.append(end);
                return sb.toString();
            } catch (Exception e) {
                return DEFAULT_PENIS;
            }
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
