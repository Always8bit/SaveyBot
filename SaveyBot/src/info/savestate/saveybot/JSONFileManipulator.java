/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.savestate.saveybot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import org.json.*;

/**
 * A database manipulator for SaveyBot's JSON database.
 * @author Joseph El-Khouri
 */
public class JSONFileManipulator {
    
    private final String filename;
    private final String logfile;
    private final Random rand;
    
    public JSONFileManipulator(String filename, String logfile) {
        this.logfile = logfile;
        this.filename = filename;
        this.rand = new Random();
    }
    
    public String getSlot(String slotString, boolean largeResponse) {
        try {
            return getSlot( new BigInteger(slotString));
        } catch (Exception e) {
            // going to load all slots made by user.
        }
        JSONArray json = getJSON();
        if (!largeResponse) {
            int entries = 0;
            for (int i=0; i<json.length(); i++) {
                JSONObject o = json.getJSONObject(i);
                if (o == null) continue;
                if (o.getString("name").toLowerCase().equals(slotString.toLowerCase())) entries++;
            } 
            if (entries > 0)
                return slotString + " owns " + entries + " savestates!!! :D/";
            return slotString + " doesnt own any savestates );";
        }
        StringBuilder slots = new StringBuilder();
        int entries = 0;
        for (int i=0; i<json.length(); i++) {
            JSONObject o = json.getJSONObject(i);
            if (o == null) continue;
            if (o.getString("name").toLowerCase().equals(slotString.toLowerCase())) {
                entries++;
                slots.append(o.getString("slot")).append(", ");
            }
        }
        if (entries > 0) {
            slots.deleteCharAt(slots.length()-1);
            slots.deleteCharAt(slots.length()-1);
            return "owha! " + slotString + " owns slot(s) " + slots.toString() + "!!!! :D :D :D/";
        }
        return slotString + " doesn't own any savestates!! (u should fix that !! O:)";
    }
    
    public String log() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html>\n");
        sb.append("    <head>\n");
        sb.append("    <title>dongs!!!!</title>\n");
        sb.append("    </head>\n");
        sb.append("    <body>\n");
        sb.append("        <h4>SaveyBot's Savestates!!! :D :D :D/</h4>\n");
        sb.append("        <br />\n");
        sb.append("        <table style='table-layout: fixed; width: 100%'><tbody class=\"list\">\n");
        
        JSONArray json = getJSON();
        
        for (int i=0; i<json.length(); i++) {
            JSONObject savestate = json.getJSONObject(i);
            sb.append("            <tr>\n");
            sb.append("                <td class=\"slot\" style='word-wrap: break-word;'>").append(savestate.getString("slot")).append("</td>\n");
            sb.append("                <td class=\"name\" style='word-wrap: break-word;'>").append(savestate.getString("name")).append("</td>\n");
            sb.append("                <td class=\"message\" style='word-wrap: break-word;'>").append(savestate.getString("message")).append("</td>\n");
            sb.append("            </tr>\n");
        }
        
        sb.append("        </tbody></table>\n");
        sb.append("    </body>\n");
        sb.append("</html>\n");
        sb.append("\n\n\n\n");
        
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date();
        sb.append("<!-- FILE GENERATED ON ").append(df.format(date)).append(" -->\n");
        
        writeLog(sb.toString());
        
        return "omg omggomg omg om TH ULTIMATE http://overcocked.penis.systems/log.html O: O: O: !"; 
    }
    
    public String remove(String slotString, String username) {
        BigInteger slot;
        try {
            slot = new BigInteger(slotString);
        } catch (Exception e) {
            return "uh hhh h-- that's not a number??? lmao bye af";
        }
        JSONArray json = getJSON();
        for (int i=0; i<json.length(); i++) {
            JSONObject savestate = json.getJSONObject(i);
            if (savestate.getString("slot").equals(slot.toString())) {
                if (savestate.getString("name").equals(username)) {
                    json.remove(i);
                    writeJSON(json);
                    return "rip ur msg )));";
                } else {
                    return "u can't do that! that savestate belongs to " + savestate.getString("name") + "! O:";
                }
            } 
        }
        return "savestate not found ! (u should make it!!!)";
    }
    
    public String size() {
        return "theres " + getJSON().length() + " savestates saved! holy wow! \\:D/";
    }
    
    public String nameList(boolean largeResponse) {
        JSONArray json = getJSON();
        ArrayList<String> names = new ArrayList<>();
        for (int i=0; i<json.length(); i++) {
            JSONObject savestate = json.getJSONObject(i);
            String name = savestate.getString("name");
            if (!names.contains(name)) 
                names.add(name);
        }
        if (largeResponse) {
            StringBuilder sb = new StringBuilder();
            names.stream().forEach((String name) -> {
                sb.append(name).append(", ");
            });
            String returnString = sb.toString();
            return "SaveyBot's personal dongs!! :D :D :D/ : " + returnString.substring(0, returnString.length()-2);
        } else {
            return "SaveyBot has " + names.size() + " personal dongs!! :D :D :D/";
        }
    }
    
    public String markOfAll(int sentenceSize) {
        JSONArray json = getJSON();
        ArrayList<String> words = new ArrayList<>();
        
        if (sentenceSize <= 0)
            sentenceSize = ((int)(Math.random()*6))+5;  
        if (sentenceSize > 50) sentenceSize = 50;
        
        for (int i=0; i<json.length(); i++) {
            JSONObject savestate = json.getJSONObject(i);
            String[] splitMessage = savestate.getString("message").split("\\s+");
            words.addAll(Arrays.asList(splitMessage));
        }
        if (words.isEmpty()) {
            return "lmao WTF this ERROR should never happen!!! (ZERO SAVESTATES ??? WTF)";
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<sentenceSize; i++) /**/ sb.append(words.get(rand.nextInt(words.size()))).append(' ');
        return sb.toString().trim();
    }
    
    public String markOf(String username, int sentenceSize) {
        username = username.trim();
        JSONArray json = getJSON();
        ArrayList<String> words = new ArrayList<>();
        
        if (sentenceSize <= 0)
            sentenceSize = ((int)(Math.random()*6))+5;  
        if (sentenceSize > 50) sentenceSize = 50;
        
        for (int i=0; i<json.length(); i++) {
            JSONObject savestate = json.getJSONObject(i);
            if (savestate.getString("name").toLowerCase().equals(username.toLowerCase())) {
                String[] splitMessage = savestate.getString("message").split("\\s+");
                words.addAll(Arrays.asList(splitMessage));
            }
        
        }
        
        if (words.isEmpty()) {
            return "helo there that person SUCKS for not having any SAVESTATES O:<";
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<sentenceSize; i++) /**/ sb.append(words.get(rand.nextInt(words.size()))).append(' ');
        return sb.toString().trim();
    }
    
    public String markOf(String command) {
        if (command.length() == 0) {
            return markOfAll(-1);
        }
        else {
            String[] words = command.split("\\s+");
            
            if (words.length == 1)  {
                try {
                    return markOfAll(Integer.parseInt(words[0]));
                } catch (Exception e) {
                    return markOf(words[0], -1);
                }
            }
            else {
                try {
                    return markOf(words[0], Integer.parseInt(words[1]));
                } catch (Exception e) {
                    return markOf(words[0], -1);
                }
            }
        }
    }

    private String getSlot(BigInteger slot) {
        JSONArray json = getJSON();
        for (int i=0; i<json.length(); i++) {
            JSONObject o = json.getJSONObject(i);
            if (o == null) continue;
            BigInteger current = new BigInteger(o.getString("slot"));
            if (current.equals(slot))
                return o.getString("message");
        }
        return "that savestate doesnt exist !!! (u should make it)";
    }
    
    public String lowestSlot() {
        BigInteger lowest = BigInteger.ZERO;
        JSONArray json = getJSON();
        boolean passed = false;
        while (!passed) {
            passed = true;
            for (int i=0; i<json.length(); i++) {
                JSONObject o = json.getJSONObject(i);
                BigInteger current = o.getBigInteger("slot");
                if (current.compareTo(lowest) == 0) {
                    lowest = lowest.add(BigInteger.ONE);
                    passed = false;
                    break;
                }
            }
        }
        return lowest.toString();
    }
    
    public String low() {
        return "SaveyBot's lowest free slot is #" + lowestSlot() + "! wow !!! :D/";
    }

    public String search(String term, boolean largeResponse) {
        int MIN_SEARCH_SIZE = 3;
        if (!(term.length() >= MIN_SEARCH_SIZE)) {
            return "\"" + term +  "\" is 2 small !! gotta add " + (MIN_SEARCH_SIZE-term.length()) + " more chars d;";
        }
        JSONArray json = getJSON(); 
        StringBuilder sb = new StringBuilder();
        int size = 0;
        for (int i=0; i<json.length(); i++) {
            JSONObject savestate = json.getJSONObject(i);
            if (savestate.getString("message").toLowerCase().contains(term.trim().toLowerCase())) {
                sb.append(savestate.getString("slot")).append(", ");
                size++;
            }
        }
        if (sb.length() == 0)
            return "no search resluts! )^:";
        if (largeResponse) return term + ": " + sb.toString().substring(0, sb.toString().length()-2);
        else return term + ": " + size + " resluts found !!! (go 2 #savespam 2 view em !)";
    }
    
    public String saveLaugh() {
        // a near copy paste from the old saveybot
        // that is why this is exceptionally ugly java
        // instead of regularly ughly javah
        int n;
        int n2;
        String string2 = "";
        String string3 = "";
        int n3;
        int n4;
        for (n = 0; n < 6; ++n) {
            n3 = new Random().nextInt((10 - 0) + 1) + 0;
            if (n3 >= 0 && n3 < 4) {
                string3 = "H";
            }
            if (n3 >= 4 && n3 < 8) {
                string3 = "A";
            }
            if (n3 == 9) {
                string3 = "h";
            }
            if (n3 == 10) {
                string3 = "a";
            }
            for (n2 = 0; n2 < n; ++n2) {
                string2 = string2 + string3;
            }
        }
        for (n = 0; n < 40; ++n) {
            n3 = new Random().nextInt((2 - 0) + 1) + 0;
            for (n2 = 0; n2 < n3; ++n2) {
                int n5;
                n4 = new Random().nextInt((3 - 0) + 1) + 0;
                if (n4 == 0) {
                    string3 = "H";
                }
                if (n4 == 1) {
                    string3 = "A";
                }
                if (n4 == 2) {
                    string3 = "h";
                }
                if (n4 == 3) {
                    string3 = "a";
                }
                if ((n5 = new Random().nextInt((1500 - 0) + 1) + 0) == 616) {
                    string3 = "P";
                    n2 = n3;
                }
                if ((n5 =  new Random().nextInt((100000 - 0) + 1) + 0) == 44231) {
                    string3 = "imGonnaKillYouYouFuckingBitch";
                    n2 = n3;
                }
                string2 = string2 + string3;
            }
        }
        return string2;
    }
            
    public String saveSlot(String username, String message) {    
        return saveSlot(lowestSlot(), username, message);
    }
    
    public String saveSlot(String slotString, String username, String message) {
        BigInteger slot;
        try {
            slot = new BigInteger(slotString);
        } catch (Exception e) {
            return "lmao bye af thats not a real number";
        }
        JSONArray json = getJSON();
        int replaceIndex = -1;
        for (int i=0; i<json.length(); i++) {
            JSONObject o = json.getJSONObject(i);
            if (o == null) continue;
            BigInteger current = new BigInteger(o.getString("slot"));
            if (current.equals(slot)) {
                if (!o.getString("name").equals(username)) {
                     return "waohwo!!! " + o.getString("name") + " owns this savestate you dong !!";
                } else {
                    replaceIndex = i;
                    break;
                }
            }
        }
        JSONObject o = new JSONObject();
        o.put("name", username);
        o.put("slot", slot.toString());
        o.put("message", message);
        if (replaceIndex != -1) {
            json.remove(replaceIndex);
            json.put(replaceIndex, o);
        } else json.put(o);
        writeJSON(json);
        return "ur savestate was sav'd to slot " + slot.toString() + "! ^O^";
    }
    
    public String whois(String slotString) {
        BigInteger slot;
        try {
            slot = new BigInteger(slotString);
        } catch (Exception e) {
            return "idk what the wtf u were doing but " + slotString + " is NOT a number lmao.";
        }
        JSONArray json = getJSON();
        for (int i=0; i<json.length(); i++) {
            JSONObject savestate = json.getJSONObject(i);
            if (savestate.getString("slot").equals(slot.toString()))
                return "savestate " + savestate.getString("slot") + 
                        " is owned by " + savestate.getString("name") +
                        "!!! ^o^ ";
        }
        return "no one owns that savestate!!! (u should change that!)";
    }
    
    public String randomLoad(String username) {
        JSONObject savestate;
        int slot;
        JSONArray json = getJSON();
        if (username.isEmpty()) {
            slot = rand.nextInt(json.length());
            savestate = json.getJSONObject(slot);
            username = json.getJSONObject(slot).getString("name");
        } else {
            JSONArray userArray = new JSONArray();
            for(int i=0; i<json.length(); i++) {
                if (json.getJSONObject(i).getString("name").toLowerCase().equals(username.toLowerCase()))
                    userArray.put(json.getJSONObject(i));
            }
            if (userArray.length() == 0)
                return username + " doesn't have any savestates!!! O:";
            slot = rand.nextInt(userArray.length());
            savestate = userArray.getJSONObject(slot);
            
        }
        return username + "[" + savestate.getString("slot") + "]: " + savestate.getString("message");
    }
    
    public String randomLoad() {
        return randomLoad("");
    }
    
    /**
     * 
     * @return Returns a JSON Array of the JSON database
     */
    private JSONArray getJSON() {
        try {
            File file = new File(filename);
            InputStream is = new FileInputStream(file);
            BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = in.readLine()) != null)
                sb.append(line).append("\n");
            JSONTokener tokenizer = new JSONTokener(sb.toString());
            return new JSONArray(tokenizer);
        }
        catch (IOException | JSONException e) {
            return null;
        }
    }
    
    /**
     * Write the modified JSON Database!
     */
    private void writeJSON(JSONArray json) {
        try {
            String jsonString = json.toString(1);
            byte[] jsonBytes = jsonString.getBytes(StandardCharsets.UTF_8);
            FileOutputStream fos = new FileOutputStream(filename);
            fos.write(jsonBytes);
            fos.close();
            System.out.println("JSON database saved to " + filename);
        } catch (IOException e) {
            System.out.println("JSON database error.");
        }
    }
    
    private void writeLog(String logInfo) {
        try {
            byte[] logBytes = logInfo.getBytes(StandardCharsets.UTF_8);
            FileOutputStream fos = new FileOutputStream(logfile);
            fos.write(logBytes);
            fos.close();
            System.out.println(".log saved to " + logfile);
        } catch (IOException e) {
            System.out.println(".log save error.");
        }
    }
    
    public String chainall(boolean verbose) {
        return chainall(verbose, -1);
    }
    
    public String chainall(boolean verbose, int sentenceSize) {
        JSONArray json = getJSON();
        ArrayList<String> words = new ArrayList<>();
        for (int i=0; i<json.length(); i++) {
            JSONObject savestate = json.getJSONObject(i);
            String[] splitMessage = savestate.getString("message").split("\\s+");
            words.addAll(Arrays.asList(splitMessage));
        }
        if (words.isEmpty()) {
            return "lmao WTF this ERROR should never happen!!! (ZERO SAVESTATES ??? WTF)";
        }
        StringBuilder sb = new StringBuilder();
        // all words are in "words"
        // pick a word out at random!
        int wordIndex = (int)(Math.random()*words.size());
        if (sentenceSize <= 0) 
            sentenceSize = ((int)(Math.random()*12))+3;
        if (sentenceSize > 50) sentenceSize = 50;
        
        for (int i=0; i<sentenceSize; i++) {
            // get current word
            sb.append(words.get(wordIndex)).append(' ');
            // 40% chance we'll stay on the same sentence.
            if (Math.random() > 0.40) {
                // different sentence
                // make an array of all the .toLowerCase() matches of the current word
                ArrayList<Integer> wordIndexes = new ArrayList<>();
                for (int j=0; j<words.size(); j++) {
                    if (words.get(j).equalsIgnoreCase(words.get(wordIndex)))
                        wordIndexes.add(j);
                }
                // we have all the word indexes in wordIndexes
                // ... if there are none, we'll just follow the current word (since it'll match)
                int sameWordIndex = (int)(Math.random()*wordIndexes.size());
                wordIndex = wordIndexes.get(sameWordIndex) + 1;
            } else {
                wordIndex++;
            }
            if (wordIndex >= words.size()) {
                // in case we go over!!!
                // new random number
                wordIndex = (int)(Math.random()*words.size());
            }
        }
        return sb.toString();        
    }
    
    public String chain(String command, boolean verbose) {
        String[] words = command.split("\\s+");
        if (words.length == 1)  {
            try {
                return markOfAll(Integer.parseInt(words[0]));
            } catch (Exception e) {
                return markOf(words[0], -1);
            }
        }
        else {
            try {
                return chain(words[0], verbose, Integer.parseInt(words[1]));
            } catch (Exception e) {
                return chain(words[0], verbose, -1);
            }
        }
    }

    public String chain(String username, boolean verbose, int sentenceSize) {
        username = username.trim();
        JSONArray json = getJSON();
        ArrayList<String> words = new ArrayList<>();
        for (int i=0; i<json.length(); i++) {
            JSONObject savestate = json.getJSONObject(i);
            if (savestate.getString("name").toLowerCase().equals(username.toLowerCase())) {
                String[] splitMessage = savestate.getString("message").split("\\s+");
                words.addAll(Arrays.asList(splitMessage));
            }
        }
        if (words.isEmpty()) {
            return "helo there that person SUCKS for not having any SAVESTATES O:<";
        }
        StringBuilder sb = new StringBuilder();
        // all words are in "words"
        // pick a word out at random!
        int wordIndex = (int)(Math.random()*words.size());
        if (sentenceSize <= 0)
            sentenceSize = ((int)(Math.random()*6))+5;  
        if (sentenceSize > 50) sentenceSize = 50;
        for (int i=0; i<sentenceSize; i++) {
            // get current word
            sb.append(words.get(wordIndex)).append(' ');
            // 40% chance we'll stay on the same sentence.
            if (Math.random() > 0.40) {
                // different sentence
                // make an array of all the .toLowerCase() matches of the current word
                ArrayList<Integer> wordIndexes = new ArrayList<>();
                for (int j=0; j<words.size(); j++) {
                    if (words.get(j).equalsIgnoreCase(words.get(wordIndex)))
                        wordIndexes.add(j);
                }
                // we have all the word indexes in wordIndexes
                // ... if there are none, we'll just follow the current word (since it'll match)
                int sameWordIndex = (int)(Math.random()*wordIndexes.size());
                wordIndex = wordIndexes.get(sameWordIndex) + 1;
            } else {
                wordIndex++;
            }
            if (wordIndex >= words.size()) {
                // in case we go over!!!
                // new random number
                wordIndex = (int)(Math.random()*words.size());
            }
        }
        return sb.toString();
    }
}
