/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.savestate.saveybot;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Joseph
 */
public class FloodProtection {
    
    public FloodProtection(SaveyBot sb) {
        Runnable floodProtection = () -> {
            for (CircleTimer ct : sb.getFloodTimers()) {
                if (ct.tooQuick() && (ct.inChannel == true)) {
                    sb.partChannel(ct.getChannel(), "go to #savespam dongs!!!");
                    ct.inChannel = false;
                    continue;
                }
                if (ct.canReturn() && (ct.inChannel == false)) {
                    sb.joinChannel(ct.getChannel());
                    ct.inChannel = true;
                    ct.zeroOut();
                } 
            }
        };
        
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(floodProtection, 10, 1, TimeUnit.SECONDS);
    }
    
}
