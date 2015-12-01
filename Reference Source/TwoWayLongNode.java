/*
 * Decompiled with CFR 0_110.
 */
public class TwoWayLongNode {
    public TwoWayLongNode next;
    public TwoWayLongNode prev;
    public long datum;

    TwoWayLongNode(long l) {
        this.datum = l;
        this.next = null;
        this.prev = null;
    }

    TwoWayLongNode(long l, TwoWayLongNode twoWayLongNode) {
        this.datum = l;
        this.next = null;
        this.prev = twoWayLongNode;
    }

    TwoWayLongNode(long l, TwoWayLongNode twoWayLongNode, TwoWayLongNode twoWayLongNode2) {
        this.datum = l;
        this.next = twoWayLongNode;
        this.prev = twoWayLongNode2;
    }
}