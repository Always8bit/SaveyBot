/*
 * Decompiled with CFR 0_110.
 */

package database;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * This is a legacy class; do not use in production code except for in
 * the Database Converter.
 * @author Joseph El-Khouri
 */
public class NameContainer implements Serializable {
    private String name;
    private String data;
    private BigInteger slot;

    /**
     * This is a legacy class that should only be used in the DatabaseConverter.
     * @param bigInteger the number of the slot this savestate occupies. 
     * @param string the name of the user saving this savestate.
     * @param string2 the message by the user saving this savestate.
     */
    public NameContainer(BigInteger bigInteger, String string, String string2) {
        this.slot = bigInteger;
        this.name = string;
        this.data = string2;
    }

    /**
     *
     * @return returns the message contained in this savestate.
     */
    public String getData() {
        return this.data;
    }

    /**
     *
     * @param string sets the message in this savestate.
     */
    public void setData(String string) {
        this.data = string;
    }

    /**
     *
     * @return returns the owner of the savestate.
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return returns the slot number of this savestate.
     */
    public BigInteger getSlot() {
        return this.slot;
    }
}