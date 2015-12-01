/*
 * Decompiled with CFR 0_110.
 */
import java.io.Serializable;
import java.math.BigInteger;

public class NameContainer
implements Serializable {
    private String name;
    private String data;
    private BigInteger slot;

    public NameContainer(BigInteger bigInteger, String string, String string2) {
        this.slot = bigInteger;
        this.name = string;
        this.data = string2;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String string) {
        this.data = string;
    }

    public String getName() {
        return this.name;
    }

    public BigInteger getSlot() {
        return this.slot;
    }
}