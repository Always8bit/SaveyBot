/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  NameSpace
 *  org.jibble.pircbot.PircBot
 */
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;
import org.jibble.pircbot.PircBot;

public class MyBot
extends PircBot {
    private NameSpace BOTBNames;
    private boolean currentlyInBotb;
    private FiveSlotCircleTimer fiveMinuteTimer;
    private long tenMinuteCountDown;
    private final String mainChannel = "#botb";
    long timeSinceLastSave;
    private MessageSpace BOTBMessages;

    public MyBot() {
        this.setName("SaveyBot");
        this.BOTBNames = new NameSpace();
        this.BOTBMessages = new MessageSpace();
        this.setMessageDelay(1200);
        this.currentlyInBotb = true;
        this.fiveMinuteTimer = new FiveSlotCircleTimer();
        this.tenMinuteCountDown = 0;
        this.BOTBNames.makeBackup();
        this.timeSinceLastSave = System.currentTimeMillis();
    }

    private String eQ(int n) {
        if (n > 800) {
            n = 800;
        }
        String string = "";
        for (int i = 0; i < n; ++i) {
            string = string + "=";
        }
        return string;
    }

    private void saveState(String string, String string2, String string3, String string4) {
        Scanner scanner = new Scanner(string);
        scanner.next();
        try {
            String string5 = scanner.next();
            BigInteger bigInteger = new BigInteger(string5);
            String string6 = scanner.nextLine();
            this.sendMessage(string3, " ~ " + this.BOTBNames.saveData(bigInteger, string2, string6));
        }
        catch (Exception var8_8) {
            scanner = new Scanner(string);
            scanner.next();
            try {
                scanner.next();
            }
            catch (Exception var9_11) {
                return;
            }
            scanner = new Scanner(string);
            scanner.next();
            String string7 = scanner.nextLine();
            this.sendMessage(string4, " ~ " + this.BOTBNames.saveData(string2, string7));
        }
        this.BOTBNames.backup();
    }

    private void loadState(String string, String string2, String string3) {
        Scanner scanner = new Scanner(string);
        scanner.next();
        try {
            String string4 = scanner.next();
            BigInteger bigInteger = new BigInteger(string4);
            this.sendMessage(string2, " ~ " + this.BOTBNames.getData(bigInteger));
        }
        catch (Exception var6_8) {
            scanner = new Scanner(string);
            scanner.next();
            String string5 = scanner.next();
            String string6 = this.BOTBNames.getData(string5);
            this.sendMessage(string3, " ~ " + string6);
        }
    }

    private void THIS(String string, String string2) {
        try {
            int n = Integer.parseInt(string.substring(6));
            if (n >= 0) {
                this.sendMessage(string2, "8" + this.eQ(n) + "D");
            } else {
                this.sendMessage(string2, "D" + this.eQ(n * -1) + "8");
            }
        }
        catch (Exception var3_4) {
            this.sendMessage(string2, "8====================D");
        }
    }

    private void DISCLAIMER(String string, String string2) {
        this.sendMessage(string2, "!DISCLAIMER: I'M STRAIGHT!!");
    }

    private void randomState(String string, String string2) {
        Scanner scanner = new Scanner(string);
        scanner.next();
        try {
            String string3 = scanner.next();
            this.sendMessage(string2, " ~ " + this.BOTBNames.getRandom(string3));
        }
        catch (Exception var5_5) {
            this.sendMessage(string2, " ~ " + this.BOTBNames.getRandom());
        }
    }

    private void markov(String string, String string2) {
        Scanner scanner = new Scanner(string);
        scanner.next();
        try {
            String string3 = scanner.next();
            this.sendMessage(string2, " ~ " + this.BOTBNames.markov(string3));
        }
        catch (Exception var5_5) {
            this.sendMessage(string2, " ~ " + this.BOTBNames.markov());
        }
    }

    private void removeSave(String string, String string2, String string3) {
        Scanner scanner = new Scanner(string);
        scanner.next();
        try {
            String string4 = scanner.next();
            BigInteger bigInteger = new BigInteger(string4);
            this.sendMessage(string3, " ~ " + this.BOTBNames.releaseSlot(bigInteger, string2));
        }
        catch (Exception var6_7) {
            this.sendMessage(string3, " ~ lol :^) that's not a savestate silly!!");
        }
        this.BOTBNames.backup();
    }

    private void removeSaveAdmin(String string, String string2) {
        Scanner scanner = new Scanner(string);
        scanner.next();
        try {
            String string3 = scanner.next();
            BigInteger bigInteger = new BigInteger(string3);
            this.sendMessage(string2, " ~ " + this.BOTBNames.releaseSlotAdmin(bigInteger));
        }
        catch (Exception var5_6) {
            this.sendMessage(string2, " ~ lol :^) that's not a savestate silly!!");
        }
        this.BOTBNames.backup();
    }

    private void whois(String string, String string2) {
        Scanner scanner = new Scanner(string);
        scanner.next();
        try {
            String string3 = scanner.next();
            BigInteger bigInteger = new BigInteger(string3);
            this.sendMessage(string2, " ~ " + this.BOTBNames.getName(bigInteger));
        }
        catch (Exception var5_6) {
            this.sendMessage(string2, " ~ lol! that's not a savestate silly willy :^)");
        }
    }

    private void search(String string, String string2) {
        Scanner scanner = new Scanner(string);
        scanner.next();
        try {
            String string3 = scanner.nextLine();
            this.sendMessage(string2, " ~ " + this.BOTBNames.search(string3));
        }
        catch (Exception var5_5) {
            this.sendMessage(string2, " ~ u egg u cant even search. are you 12??? WOwowWwowowwo");
        }
    }

    private void removeAllSaves(String string, String string2, String string3) {
        Scanner scanner = new Scanner(string);
        scanner.next();
        try {
            String string4 = scanner.next();
            this.sendMessage(string3, " ~ " + this.BOTBNames.releaseAll(string4, string2));
        }
        catch (Exception var6_6) {
            // empty catch block
        }
        if (string.equals(".removeall")) {
            this.sendMessage(string3, " ~ " + this.BOTBNames.releaseAll(string2));
        }
        this.BOTBNames.backup();
    }

    private void removeAllSavesString(String string, String string2, String string3) {
        Scanner scanner = new Scanner(string);
        scanner.next();
        try {
            if (scanner.hasNext()) {
                String string4 = scanner.nextLine();
                this.sendMessage(string3, " ~ " + this.BOTBNames.releaseAllString(string4, string2));
            } else {
                this.sendMessage(string3, " ~ lol i need teH PARAMS u goof!");
            }
        }
        catch (Exception var6_6) {
            // empty catch block
        }
        this.BOTBNames.backup();
    }

    private void sizeCheck(String string) {
        this.sendMessage(string, " ~ the SaveyBot has " + this.BOTBNames.getSize() + " Savestates saved! wow! o:");
    }

    private void low(String string) {
        this.sendMessage(string, " ~ SaveyBot's tinyiestest free savestate is #" + this.BOTBNames.lowestSlot() + "!!! :O");
    }

    private void names(String string) {
        String string2 = this.BOTBNames.getNames();
        while (string2.length() > 350) {
            String string3 = string2.substring(0, 349);
            string2 = string2.substring(349);
            this.sendMessage(string, " ~ all of Savey's personal dongs!!! :D :D/ " + string3);
        }
        if (!string2.equals("")) {
            this.sendMessage(string, " ~ all of Savey's personal dongs!!! :D :D/ " + string2);
        }
    }

    private void logSAVE(String string) {
        this.sendMessage(string, " ~ the ultimate ooomg " + this.BOTBNames.saveLog() + " !!! :O holy crap holr crap hory carp O: ");
    }

    private void sendHelp(String string) {
        this.sendMessage(string, "lol u n00b (^: http://savestate.info/upload/IRCHelp.png");
    }

    private void savestateLaugh(String string) {
        int n;
        int n2;
        String string2 = "";
        String string3 = "";
        int n3 = 0;
        int n4 = 0;
        for (n = 0; n < 6; ++n) {
            n3 = MyBot.randInt(0, 10);
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
            n3 = MyBot.randInt(0, n);
            for (n2 = 0; n2 < n; ++n2) {
                string2 = string2 + string3;
            }
        }
        for (n = 0; n < 40; ++n) {
            n3 = MyBot.randInt(0, 2);
            for (n2 = 0; n2 < n3; ++n2) {
                int n5;
                n4 = MyBot.randInt(0, 3);
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
                if ((n5 = MyBot.randInt(0, 1500)) == 616) {
                    string3 = "P";
                    n2 = n3;
                }
                if ((n5 = MyBot.randInt(0, 100000)) == 44231) {
                    string3 = "imGonnaKillYouYouFuckingBitch";
                    n2 = n3;
                }
                string2 = string2 + string3;
            }
        }
        this.sendMessage(string, " ~ " + string2);
    }

    public static int randInt(int n, int n2) {
        Random random = new Random();
        int n3 = random.nextInt(n2 - n + 1) + n;
        return n3;
    }

    private void queMessage(String string, String string2, String string3) {
        Scanner scanner = new Scanner(string);
        scanner.next();
        try {
            String string4 = scanner.next();
            String string5 = scanner.nextLine();
            this.sendMessage(string3, " ~ " + this.BOTBMessages.addMessage(string5, string2, string4));
        }
        catch (Exception var7_7) {
            this.sendMessage(string3, " ~ im pretty sure that's not how u send a message");
        }
    }

    private void unqueMessage(String string, String string2, String string3) {
        Scanner scanner = new Scanner(string);
        scanner.next();
        try {
            String string4 = scanner.next();
            this.sendMessage(string3, " ~ " + this.BOTBMessages.removeMessage(string2, string4));
        }
        catch (Exception var6_6) {
            this.sendMessage(string3, " ~ lol faLE u messed up trying to remov a message");
        }
    }

    public void onMessage(String string, String string2, String string3, String string4, String string5) {
        if (string.equals("#botb")) {
            if (string5.equals(".savelaugh")) {
                this.savestateLaugh(string);
            }
            if (string5.startsWith(".save ") && string5.length() > 6) {
                this.saveState(string5, string2, string2, string2);
            }
            if (string5.startsWith(".load ") && string5.length() > 6) {
                this.loadState(string5, string, string2);
            }
            if (string5.startsWith("!THIS") || string5.startsWith("!this")) {
                this.THIS(string5, string);
            }
            if (string5.startsWith("!disclaimer") || string5.startsWith("!DISCLAIMER")) {
                this.DISCLAIMER(string5, string);
            }
            if (string5.startsWith(".road")) {
                this.randomState(string5, string);
            }
            if (string5.startsWith(".markov") && string5.length() > 6) {
                this.markov(string5, string);
            }
            if (string5.equals(".help") || string5.equals("!helpmepleaseireallycantfigurethisoutgoddamnit") || string5.equals("!pleh") || string5.equals(".assist") || string5.equals(".aid") || string5.equals(".pleh") || string5.equals(".support") || string5.equals(".advice") || string5.equals(".service") || string5.equals(".maintenance") || string5.equals(".nourishment")) {
                this.sendHelp(string2);
            }
            if ((string5.startsWith(".save ") || string5.startsWith(".load ") || string5.startsWith("!THIS") || string5.startsWith("!this") || string5.startsWith(".savelaugh") || string5.startsWith(".road") || string5.startsWith(".markov")) && this.fiveMinuteTimer.logMessageTime()) {
                this.currentlyInBotb = false;
                this.tenMinuteCountDown = System.currentTimeMillis();
                String string6 = " go to #savespam dongs!!!!";
                this.partChannel("#botb", string6);
            }
        } else if (string.equals("#savespam")) {
            String string7 = string;
            if (string5.equals(".savelaugh")) {
                this.savestateLaugh(string7);
            }
            if (string5.startsWith(".save ") && string5.length() > 6) {
                this.saveState(string5, string2, string7, string7);
            }
            if (string5.startsWith(".load ") && string5.length() > 6) {
                this.loadState(string5, string7, string7);
            }
            if (string5.startsWith(".remove ") && string5.length() > 8) {
                this.removeSave(string5, string2, string7);
            }
            if (string5.startsWith(".whois ") && string5.length() > 7) {
                this.whois(string5, string7);
            }
            if (string5.startsWith(".removeall") && !string5.contains(".removeallstring")) {
                this.removeAllSaves(string5, string2, string7);
            }
            if (string5.startsWith(".removeallstring ")) {
                this.removeAllSavesString(string5, string2, string7);
            }
            if (string5.startsWith(".road")) {
                this.randomState(string5, string7);
            }
            if (string5.startsWith(".markov") && string5.length() > 6) {
                this.markov(string5, string7);
            }
            if (string5.startsWith(".search ") && string5.length() > 7) {
                this.search(string5, string7);
            }
            if (string5.startsWith(".low")) {
                this.low(string7);
            }
            if (string5.startsWith(".log")) {
                this.logSAVE(string7);
            }
            if (string5.startsWith(".names")) {
                this.names(string7);
            }
            if (string5.startsWith("!THIS") || string5.startsWith("!this")) {
                this.THIS(string5, string7);
            }
            if (string5.startsWith("!disclaimer") || string5.startsWith("!DISCLAIMER")) {
                this.DISCLAIMER(string5, string7);
            }
            if (string5.equals(".help") || string5.equals("!helpmepleaseireallycantfigurethisoutgoddamnit") || string5.equals("!pleh") || string5.equals(".assist") || string5.equals(".aid") || string5.equals(".pleh") || string5.equals(".support") || string5.equals(".advice") || string5.equals(".service") || string5.equals(".maintenance") || string5.equals(".nourishment")) {
                this.sendHelp(string7);
            }
            if (string5.equals(".size")) {
                this.sizeCheck(string7);
            }
            if (string5.startsWith(".message ") && string5.length() > 9) {
                this.queMessage(string5, string2, string7);
            }
            if (string5.startsWith(".removemessage ") && string5.length() > 15) {
                this.unqueMessage(string5, string2, string7);
            }
            int n = this.BOTBMessages.checkForPendingMessages(string2);
            for (int i = 0; i < n; ++i) {
                String[] arrstring = this.BOTBMessages.readAndRemoveFirstFoundMessage(string2);
                this.sendMessage(string7, " ~ " + arrstring[0]);
                this.sendMessage(string7, " ~ " + arrstring[1]);
            }
            if (!this.currentlyInBotb && System.currentTimeMillis() - this.tenMinuteCountDown > 600000) {
                this.currentlyInBotb = true;
                this.joinChannel("#botb");
            }
        }
    }

    public void onPrivateMessage(String string, String string2, String string3, String string4) {
        String string5 = string;
        if (string4.equals(".savelaugh")) {
            this.savestateLaugh(string5);
        }
        if (string4.startsWith(".removeadmin")) {
            this.removeSaveAdmin(string4, string5);
        }
        if (string4.startsWith(".save ") && string4.length() > 6) {
            this.saveState(string4, string, string5, string5);
        }
        if (string4.startsWith(".load ") && string4.length() > 6) {
            this.loadState(string4, string5, string5);
        }
        if (string4.startsWith(".remove ") && string4.length() > 8) {
            this.removeSave(string4, string, string5);
        }
        if (string4.startsWith(".whois ") && string4.length() > 7) {
            this.whois(string4, string5);
        }
        if (string4.startsWith(".removeall") && !string4.contains(".removeallstring")) {
            this.removeAllSaves(string4, string, string5);
        }
        if (string4.startsWith(".removeallstring ")) {
            this.removeAllSavesString(string4, string, string5);
        }
        if (string4.startsWith(".road")) {
            this.randomState(string4, string5);
        }
        if (string4.startsWith(".markov") && string4.length() > 6) {
            this.markov(string4, string5);
        }
        if (string4.startsWith(".search ") && string4.length() > 7) {
            this.search(string4, string5);
        }
        if (string4.startsWith(".low")) {
            this.low(string5);
        }
        if (string4.startsWith(".log")) {
            this.logSAVE(string5);
        }
        if (string4.startsWith(".names")) {
            this.names(string5);
        }
        if (string4.startsWith("!THIS") || string4.startsWith("!this")) {
            this.THIS(string4, string5);
        }
        if (string4.startsWith("!disclaimer") || string4.startsWith("!DISCLAIMER")) {
            this.DISCLAIMER(string4, string5);
        }
        if (string4.equals(".help") || string4.equals("!helpmepleaseireallycantfigurethisoutgoddamnit") || string4.equals("!pleh") || string4.equals(".assist") || string4.equals(".aid") || string4.equals(".pleh") || string4.equals(".support") || string4.equals(".advice") || string4.equals(".service") || string4.equals(".maintenance") || string4.equals(".nourishment")) {
            this.sendHelp(string5);
        }
        if (string4.equals(".size")) {
            this.sizeCheck(string5);
        }
        if (!this.currentlyInBotb && System.currentTimeMillis() - this.tenMinuteCountDown > 600000) {
            this.currentlyInBotb = true;
            this.joinChannel("#botb");
        }
        if (string4.startsWith(".message ") && string4.length() > 9) {
            this.queMessage(string4, string, string5);
        }
        if (string4.startsWith(".removemessage ") && string4.length() > 15) {
            this.unqueMessage(string4, string, string5);
        }
    }

    public void onDisconnect() {
        boolean bl = true;
        while (bl) {
            try {
                this.reconnect();
                bl = false;
            }
            catch (Exception var2_2) {
                bl = true;
            }
        }
        this.joinChannel("#savespam");
        this.joinChannel("#botb");
    }

    public void onServerPing(String string) {
        super.onServerPing(string);
        if (!this.currentlyInBotb && System.currentTimeMillis() - this.tenMinuteCountDown > 600000) {
            this.currentlyInBotb = true;
            this.joinChannel("#botb");
        }
        if (System.currentTimeMillis() - this.timeSinceLastSave > 5000000) {
            this.BOTBNames.makeBackup();
            this.timeSinceLastSave = System.currentTimeMillis();
        }
    }

    public void onJoin(String string, String string2, String string3, String string4) {
        if (string.equals("#savespam")) {
            this.sendMessage(string, " ~ hello " + string2 + "!!! (aka a big dong!!) (^: welcome 2 tEH (hashtag)SAVE SPAM");
            this.sendMessage(string, " ~ type in .help TO GET [ASS]ISTANCE [u can pm me too if it's too spammy in here!!!]");
            int n = this.BOTBMessages.checkForPendingMessages(string2);
            for (int i = 0; i < n; ++i) {
                String[] arrstring = this.BOTBMessages.readAndRemoveFirstFoundMessage(string2);
                this.sendMessage("#savespam", " ~ " + arrstring[0]);
                this.sendMessage("#savespam", " ~ " + arrstring[1]);
            }
            if (string2.equals("SaveyBot")) {
                this.sendMessage("NickServ", "identify SaveyBot REDACTED_PASS");
            }
        }
    }

    public void onNickChange(String string, String string2, String string3, String string4) {
        int n = this.BOTBMessages.checkForPendingMessages(string4);
        for (int i = 0; i < n; ++i) {
            String[] arrstring = this.BOTBMessages.readAndRemoveFirstFoundMessage(string4);
            this.sendMessage("#savespam", " ~ " + arrstring[0]);
            this.sendMessage("#savespam", " ~ " + arrstring[1]);
        }
    }
}