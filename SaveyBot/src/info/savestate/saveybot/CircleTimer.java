/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.savestate.saveybot;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author Joseph
 */
public class CircleTimer {
    
    private final TimerNode[] nodes;
    private final String channel;
    private final int threshold;
    private final int leaveFor;
    public boolean inChannel;

    private int currentSlot;
    
    public CircleTimer(int size, int threshold, int leaveFor, String channel) {
        nodes = new TimerNode[size];
        for (int i=0; i<size; i++) {
            nodes[i] = new TimerNode();
        }
        for (int i=0; i<size; i++) {
            if (i == 0) {
                nodes[i].next = null;
                nodes[i].prev = null;
            } else if (i < size-1) {
                nodes[i].next   = null;
                nodes[i].prev   = nodes[i-1];
                nodes[i-1].next = nodes[i];
            } else {
                nodes[i].next   = nodes[0];
                nodes[i].prev   = nodes[i-1];
                nodes[i-1].next = nodes[i];
                nodes[0].prev   = nodes[i];
            }
        }
        currentSlot    = 0;
        this.threshold = threshold;
        this.leaveFor  = leaveFor;
        this.channel   = channel;
        this.inChannel = true;
    }
    
    public String getChannel() {
        return channel;
    }
    
    public void zeroOut() {
        for(TimerNode tn : nodes) tn.time = 0;
    }
    
    public void tick() {
        currentSlot = (currentSlot + 1) % nodes.length;
        nodes[currentSlot].setSeconds(); 
    }
    
    private long previousTime() {
        return nodes[(currentSlot+1) % nodes.length].time;
    }
    
    public boolean tooQuick() {
        long currentTime  = nodes[currentSlot].time;
        long previousTime = previousTime();
        if ((currentTime == 0) || (previousTime == 0))
            return false;
        return currentTime - previousTime < threshold;
    }
    
    public boolean canReturn() {
        long currentTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        long previousTime = nodes[currentSlot].time;
        return currentTime - previousTime > leaveFor;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<nodes.length; i++) {
            sb.append("[").append(i).append("] - ").append(channel).append("TIME: ").append(nodes[i].time)
                    .append("\nTHIS: ").append(nodes[i].toString())
                    .append("\nNEXT: ").append(nodes[i].next.toString())
                    .append("\nPREV: ").append(nodes[i].prev.toString()).append("\n");
        }
        sb.append("CURRENT TIME: ").append(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())).append("\n");
        long currentTime  = nodes[currentSlot].time;
        long previousTime = previousTime();
        sb.append("TOP: ").append(currentTime).append("\n");
        sb.append("BOT: ").append(previousTime).append("\n");
        sb.append("T[").append(threshold).append("] L4[").append(leaveFor).append("] DIF[").append(currentTime - previousTime).append("]");
        return sb.toString();
    }
    
}
