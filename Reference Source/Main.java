/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  MyBot
 */
public class Main {
    public static void main(String[] arrstring) throws Exception {
        MyBot myBot = new MyBot();
        myBot.setVerbose(true);
        myBot.connect("damocles.esper.net");
        myBot.joinChannel("#botb");
        myBot.joinChannel("#savespam");
    }
}