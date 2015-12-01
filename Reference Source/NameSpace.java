/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.apache.commons.net.ftp.FTPClient
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import org.apache.commons.net.ftp.FTPClient;

public class NameSpace {
    private ArrayList<NameContainer> peoples;
    private BigInteger lowestAvaliableSlot;

    public NameSpace() {
        try {
            FileInputStream fileInputStream = new FileInputStream("savestates.sav");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            this.peoples = (ArrayList)objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        }
        catch (IOException var1_2) {
            this.peoples = new ArrayList();
            this.backup();
        }
        catch (ClassNotFoundException var1_3) {
            System.out.println("NameContainer ArrayList class not found");
            var1_3.printStackTrace();
        }
        this.lowestAvaliableSlot = new BigInteger("0");
        while (this.doesSlotExist(this.lowestAvaliableSlot) != -1) {
            this.lowestAvaliableSlot = this.lowestAvaliableSlot.add(BigInteger.ONE);
        }
    }

    public String saveLog() {
        String string = "<!DOCTYPE html><html><head><title>dongs!!!!</title></head><body><h4>SaveyBot's Savestates!!! :D :D :D/</h4><br /><table style='table-layout: fixed; width: 100%'>";
        for (int i = 0; i < this.peoples.size(); ++i) {
            string = string.concat("<tr><td style='word-wrap: break-word;'>" + this.peoples.get(i).getSlot().toString() + "</td>" + "<td style='word-wrap: break-word;'>" + this.peoples.get(i).getName() + "</td>" + "<td style='word-wrap: break-word;'>" + this.peoples.get(i).getData() + "</td></tr>");
        }
        string = string.concat("</table></body></html>");
        try {
            PrintWriter printWriter = new PrintWriter("log.html");
            printWriter.println(string);
            printWriter.close();
            FTPClient fTPClient = new FTPClient();
            FileInputStream fileInputStream = null;
            try {
                fTPClient.connect("WWW.REDACTED.URL");
                fTPClient.login("REDACTED_USERNAME", "REDACTED_PASSWORD");
                fTPClient.setFileType(2, 2);
                fTPClient.setFileTransferMode(2);
                String string2 = "log.html";
                fileInputStream = new FileInputStream(string2);
                fTPClient.changeWorkingDirectory("public_html/upload");
                fTPClient.storeFile("log.html", (InputStream)fileInputStream);
                fileInputStream.close();
                fTPClient.logout();
            }
            catch (IOException var5_8) {
                var5_8.printStackTrace();
            }
        }
        catch (Exception var2_4) {
            var2_4.printStackTrace();
            return "ERROR.COM OMGG NOOOOO";
        }
        return "http://www.savestate.info/upload/log.html";
    }

    public void makeBackup() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("" + System.currentTimeMillis() + ".sav");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.peoples);
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Serialized backup is saved in " + System.currentTimeMillis() + ".sav");
        }
        catch (IOException var1_2) {
            System.out.println("--------------errorsavingData------------");
        }
    }

    public int getSize() {
        return this.peoples.size();
    }

    public String lowestSlot() {
        return this.lowestAvaliableSlot.toString();
    }

    public String saveData(String string, String string2) {
        return this.saveData(this.lowestAvaliableSlot, string, string2);
    }

    public String saveData(BigInteger bigInteger, String string, String string2) {
        int n = this.doesSlotExist(bigInteger);
        if (n != -1) {
            if (this.peoples.get(n).getName().equals(string)) {
                this.peoples.get(n).setData(string2);
                return "u sav'd state tO SLOT " + bigInteger.toString() + "!!!";
            }
            return "you can not save to this slot! it belongs to " + this.peoples.get(n).getName() + " WOwOWowo";
        }
        this.peoples.add(new NameContainer(bigInteger, string, string2));
        while (this.doesSlotExist(this.lowestAvaliableSlot) != -1) {
            this.lowestAvaliableSlot = this.lowestAvaliableSlot.add(BigInteger.ONE);
        }
        return "savestate SAV'D teH new SLOT " + bigInteger.toString() + "!!!";
    }

    public String getData(String string) {
        String string2 = "";
        for (int i = 0; i < this.peoples.size(); ++i) {
            if (!this.peoples.get(i).getName().equals(string)) continue;
            string2 = string2.concat(this.peoples.get(i).getSlot().toString() + ", ");
        }
        if (string2.equals("")) {
            return string + " doesn't have any savestates!!!";
        }
        return string + " owns savestates " + string2.substring(0, string2.length() - 2);
    }

    public String getNames() {
        int n;
        String string = "";
        ArrayList<String> arrayList = new ArrayList<String>();
        for (n = 0; n < this.peoples.size(); ++n) {
            if (arrayList.contains(this.peoples.get(n).getName())) continue;
            arrayList.add(this.peoples.get(n).getName());
        }
        for (n = 0; n < arrayList.size(); ++n) {
            string = string.concat((String)arrayList.get(n) + ", ");
        }
        return string.substring(0, string.length() - 2);
    }

    public String getData(BigInteger bigInteger) {
        int n = this.doesSlotExist(bigInteger);
        if (n == -1) {
            return "the savestate doesn't exist! (you should make it!!!!)";
        }
        return this.peoples.get(n).getData();
    }

    public String getRandom() {
        Random random = new Random();
        int n = random.nextInt(this.peoples.size());
        return this.peoples.get(n).getName() + "[" + this.peoples.get(n).getSlot().toString() + "]: " + this.peoples.get(n).getData();
    }

    public String getRandom(String string) {
        if (this.doesNameExist(string) != -1) {
            int n;
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            Random random = new Random();
            for (n = 0; n < this.peoples.size(); ++n) {
                if (!this.peoples.get(n).getName().equals(string)) continue;
                arrayList.add(n);
            }
            if (arrayList.size() == 0) {
                return "u dong! they have no saves!!!!! O:<";
            }
            n = random.nextInt(arrayList.size());
            return this.peoples.get((Integer)arrayList.get(n)).getName() + "[" + this.peoples.get((Integer)arrayList.get(n)).getSlot().toString() + "]: " + this.peoples.get((Integer)arrayList.get(n)).getData();
        }
        return "u dong! they have no saves!!!!! O:<";
    }

    public String getName(BigInteger bigInteger) {
        int n = this.doesSlotExist(bigInteger);
        if (n == -1) {
            return "the savestate doesn't exist! (you should make it!!!!)";
        }
        return "le savestate slot OWNED BY " + this.peoples.get(n).getName() + " :D :D/";
    }

    public String releaseSlot(BigInteger bigInteger, String string) {
        int n = this.doesSlotExist(bigInteger);
        if (n == -1) {
            return "you cant' release a savestate that doesn't exist!! O:<";
        }
        if (this.peoples.get(n).getName().equals(string)) {
            this.peoples.remove(n);
            if (bigInteger.compareTo(this.lowestAvaliableSlot) == -1 && bigInteger.compareTo(BigInteger.ZERO) == 1) {
                this.lowestAvaliableSlot = bigInteger;
            }
            return "you DELETED YOUR SAVESTATE :O [it was slot " + bigInteger.toString() + "]";
        }
        return "trying TO DELETE OTHER BOTBRs STUFF Eh??? EH EH EH EH eH?????";
    }

    public String releaseSlotAdmin(BigInteger bigInteger) {
        int n = this.doesSlotExist(bigInteger);
        if (n == -1) {
            return "you cant' release a savestate that doesn't exist!! O:<";
        }
        this.peoples.remove(n);
        if (bigInteger.compareTo(this.lowestAvaliableSlot) == -1 && bigInteger.compareTo(BigInteger.ZERO) == 1) {
            this.lowestAvaliableSlot = bigInteger;
        }
        return "you DELETED A SAVESTATE :O [it was slot " + bigInteger.toString() + "]";
    }

    public String releaseAll(String string, String string2) {
        int n = 0;
        for (int i = 0; i < this.peoples.size(); ++i) {
            if (!this.peoples.get(i).getName().equals(string2) || !this.peoples.get(i).getSlot().toString().startsWith(string)) continue;
            if (this.peoples.get(i).getSlot().compareTo(this.lowestAvaliableSlot) == -1 && this.peoples.get(i).getSlot().compareTo(BigInteger.ZERO) == 1) {
                this.lowestAvaliableSlot = this.peoples.get(i).getSlot();
            }
            this.peoples.remove(i);
            ++n;
            --i;
        }
        return "WOW! SAD to see the Saves GO! )^; you removed " + n + " savestates.";
    }

    public String releaseAllString(String string, String string2) {
        int n = 0;
        if (string.equals("")) {
            return "WOW! SAD to see the Saves GO! )^; you removed 0 savestates.";
        }
        for (int i = 0; i < this.peoples.size(); ++i) {
            if (!this.peoples.get(i).getName().equals(string2) || !this.peoples.get(i).getData().toLowerCase().contains(string.toLowerCase())) continue;
            if (this.peoples.get(i).getSlot().compareTo(this.lowestAvaliableSlot) == -1 && this.peoples.get(i).getSlot().compareTo(BigInteger.ZERO) == 1) {
                this.lowestAvaliableSlot = this.peoples.get(i).getSlot();
            }
            this.peoples.remove(i);
            ++n;
            --i;
        }
        return "WOW! SAD to see the Saves GO! )^; you removed " + n + " savestates.";
    }

    public String releaseAll(String string) {
        int n = 0;
        for (int i = 0; i < this.peoples.size(); ++i) {
            if (!this.peoples.get(i).getName().equals(string)) continue;
            if (this.peoples.get(i).getSlot().compareTo(this.lowestAvaliableSlot) == -1 && this.peoples.get(i).getSlot().compareTo(BigInteger.ZERO) == 1) {
                this.lowestAvaliableSlot = this.peoples.get(i).getSlot();
            }
            this.peoples.remove(i);
            ++n;
            --i;
        }
        return "WOW! SAD to see the Saves GO! )^; you removed " + n + " savestates.";
    }

    private int doesSlotExist(BigInteger bigInteger) {
        for (int i = 0; i < this.peoples.size(); ++i) {
            if (this.peoples.get(i).getSlot().compareTo(bigInteger) != 0) continue;
            return i;
        }
        return -1;
    }

    private int doesNameExist(String string) {
        for (int i = 0; i < this.peoples.size(); ++i) {
            if (!this.peoples.get(i).getName().equals(string)) continue;
            return i;
        }
        return -1;
    }

    public String markov(String string) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < this.peoples.size(); ++i) {
            if (!this.peoples.get(i).getName().equals(string)) continue;
            Scanner scanner = new Scanner(this.peoples.get(i).getData());
            while (scanner.hasNext()) {
                arrayList.add(scanner.next());
            }
        }
        if (arrayList.isEmpty()) {
            return "you dong! they have no savestates!";
        }
        Random random = new Random();
        String string2 = "";
        for (int j = 0; j < 5; ++j) {
            int n = random.nextInt(arrayList.size());
            string2 = string2.concat((String)arrayList.get(n)) + " ";
        }
        return string2;
    }

    public String markov() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < this.peoples.size(); ++i) {
            Scanner scanner = new Scanner(this.peoples.get(i).getData());
            while (scanner.hasNext()) {
                arrayList.add(scanner.next());
            }
        }
        Random random = new Random();
        String string = "";
        for (int j = 0; j < 6; ++j) {
            int n = random.nextInt(arrayList.size());
            string = string.concat((String)arrayList.get(n)) + " ";
        }
        return string;
    }

    public String search(String string) {
        String string2 = "";
        for (int i = 0; i < this.peoples.size(); ++i) {
            if (!this.peoples.get(i).getData().toLowerCase().contains(string.toLowerCase())) continue;
            string2 = string2.concat(this.peoples.get(i).getSlot().toString() + ", ");
        }
        if (string2.equals("")) {
            return string + " no saves match u LOZER!!! ( lol make one that does (((^; )";
        }
        return string + ":  " + string2.substring(0, string2.length() - 2);
    }

    public void backup() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("savestates.sav");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.peoples);
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Serialized data is saved in savestates.sav");
        }
        catch (IOException var1_2) {
            System.out.println("--------------errorsavingData------------");
        }
    }
}