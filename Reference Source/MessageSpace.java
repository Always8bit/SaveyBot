/*
 * Decompiled with CFR 0_110.
 */
import java.util.ArrayList;

public class MessageSpace {
    private ArrayList<MessageContainer> messages = new ArrayList();

    public String addMessage(String string, String string2, String string3) {
        int n;
        int n2 = 0;
        for (n = 0; n < this.messages.size(); ++n) {
            if (!this.messages.get(n).from().toLowerCase().equals(string2.toLowerCase())) continue;
            ++n2;
        }
        if (n2 >= 2) {
            return "lol sorriez!!! u already have 2 messages in teH qUE :D :D/";
        }
        n2 = 0;
        for (n = 0; n < this.messages.size(); ++n) {
            if (!this.messages.get(n).to().toLowerCase().equals(string3.toLowerCase()) || !this.messages.get(n).from().toLowerCase().equals(string2.toLowerCase())) continue;
            ++n2;
        }
        if (n2 > 0) {
            return "wow! you can only send one message to one person wowowow";
        }
        this.messages.add(new MessageContainer(string2, string3, string));
        return ":D message from u (" + string2 + ") que'd for " + string3 + "!! O;";
    }

    public String removeMessage(String string, String string2) {
        int n = -1;
        for (int i = 0; i < this.messages.size(); ++i) {
            if (!this.messages.get(i).to().toLowerCase().equals(string2.toLowerCase()) || !this.messages.get(i).from().toLowerCase().equals(string.toLowerCase())) continue;
            n = i;
        }
        if (n == -1) {
            return "ahhah ahahahahahahaahahhahha can't find msg (^;";
        }
        this.messages.remove(n);
        return "wowowow! you delet'd your message!?!?! hm you should write another!! :D/";
    }

    public int checkForPendingMessages(String string) {
        int n = 0;
        for (int i = 0; i < this.messages.size(); ++i) {
            if (!this.messages.get(i).to().toLowerCase().equals(string.toLowerCase())) continue;
            ++n;
        }
        return n;
    }

    public String[] readAndRemoveFirstFoundMessage(String string) {
        int n = -1;
        for (int i = 0; i < this.messages.size() && n == -1; ++i) {
            if (!this.messages.get(i).to().toLowerCase().equals(string.toLowerCase())) continue;
            n = i;
        }
        String[] arrstring = new String[]{"Hi " + this.messages.get(n).to() + "!!! you have a message from " + this.messages.get(n).from() + "! O:", "dear " + this.messages.get(n).to() + "," + this.messages.get(n).getMessage()};
        this.messages.remove(n);
        return arrstring;
    }
}