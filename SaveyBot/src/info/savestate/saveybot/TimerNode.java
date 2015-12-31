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
public class TimerNode {
    
    public TimerNode next;
    public TimerNode prev;
    public long time;
    
    public TimerNode() {
        time = 0;
    }
    
    public void setSeconds() {
        time = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }
    
}
