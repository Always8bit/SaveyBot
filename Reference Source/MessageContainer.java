/*
 * Decompiled with CFR 0_110.
 */
public class MessageContainer {
    private String message;
    private String from;
    private String to;

    public MessageContainer(String string, String string2, String string3) {
        this.message = string3;
        this.from = string;
        this.to = string2;
    }

    public String getMessage() {
        return this.message;
    }

    public String from() {
        return this.from;
    }

    public String to() {
        return this.to;
    }
}