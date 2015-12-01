/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  TwoWayLongNode
 */
public class FiveSlotCircleTimer {
    private TwoWayLongNode currentLocation = new TwoWayLongNode(0);

    public FiveSlotCircleTimer() {
        this.currentLocation = this.currentLocation.next = new TwoWayLongNode(0, this.currentLocation);
        this.currentLocation = this.currentLocation.next = new TwoWayLongNode(0, this.currentLocation);
        this.currentLocation = this.currentLocation.next = new TwoWayLongNode(0, this.currentLocation);
        this.currentLocation = this.currentLocation.next = new TwoWayLongNode(0, this.currentLocation);
        this.currentLocation.next = this.currentLocation.prev.prev.prev.prev;
        this.currentLocation.next.prev = this.currentLocation;
    }

    boolean logMessageTime() {
        this.currentLocation.datum = System.currentTimeMillis();
        this.currentLocation = this.currentLocation.next;
        if (this.currentLocation.prev.datum - this.currentLocation.datum < 300000) {
            return true;
        }
        return false;
    }
}