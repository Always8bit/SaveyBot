package info.savestate.saveybot;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Configuration options reader. Provides a useful interface for reading the
 * configuration file for SaveyBot.
 * @author Joseph El-Khoui
 */
public class ConfigReader {
    
    private ArrayList<ConfigParamater> config;
    
    public ConfigReader(String configFilePath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(configFilePath));
        Scanner sc = new Scanner(new String(encoded, Charset.defaultCharset()));
        config = new ArrayList<>();
        while(sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.startsWith(";"))
                continue;
            if (!line.contains("="))
                continue;
            String[] split = line.split("=", 2);
            split[0] = split[0].trim().toLowerCase();
            String[] values = split[1].trim().split(" ");
            config.add(new ConfigParamater(split[0], values));
        }
    }
    
    String[] getParam(String name) {
        for (ConfigParamater param : config) {
            if (param.getName().equals(name.toLowerCase()))
                return param.getValues();
        }
        return null;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total Config Entries: ").append(config.size()).append("\n");
        config.stream().map((ConfigParamater param) -> {
            String[] values = param.getValues();
            sb.append('[');
            sb.append(param.getName());
            sb.append("] Size: ").append(values.length).append("\n");
            return values;
        }).forEach((String[] values) -> {
            for (String value : values) {
                sb.append(" | ");
                sb.append(value);
                sb.append("\n");
            }
        });
        return sb.toString();
    }
    
}
