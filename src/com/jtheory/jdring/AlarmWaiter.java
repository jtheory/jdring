/*
 *  com/jtheory/jdring/AlarmWaiter.java
 *  Copyright (C) 1999 - 2004 jtheory creations, Olivier Dedieu et al.
 *
 *  This library is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Library General Public License as published
 *  by the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Library General Public License for more details.
 *
 *  You should have received a copy of the GNU Library General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package com.jtheory.jdring;

/**
 * This class manages the thread which sleeps until the next alarm.
 * Methods are synchronized to prevent interference from the AlarmWaiter
 * thread and external threads.
 *
 * @author  Olivier Dedieu, David Sims, Jim Lerner, Rob Whelan
 * @version 1.4, 2004/04/02
 */
public class AlarmWaiter implements Runnable {
    protected AlarmManager mgr;
    protected Thread thread;
    private long sleepUntil = -1;
    private boolean debug = false;
    private boolean shutdown = false;
    
    private void debug(String s) {
        if (debug)
            System.out.println("[" + Thread.currentThread().getName() + "] AlarmWaiter: " + s);
    }
    
    /**
     * Creates a new AlarmWaiter.
     *
     * @param isDaemon true if the waiter thread should run as a daemon.
     * @param threadName the name of the waiter thread
     */
    public AlarmWaiter(AlarmManager mgr, boolean isDaemon, String waiterName) {
        this.mgr = mgr;
        
        // start the thread
        thread = new Thread(this, waiterName);
        thread.setPriority( 1 );
        thread.setDaemon(isDaemon);
        thread.start();
    }
    
    /**
     * Updates the time to sleep.
     *
     * @param _sleep_until the new time to sleep until.
     */
    public synchronized void update(long _sleep_until) {
        this.sleepUntil = _sleep_until;
        debug("Update for " + _sleep_until); // timeToSleep);
        debug("calling notify() to update thread wait timeout");
        notify();
    }
    
    /**
     * Restarts the thread for a new time to sleep until.
     *
     * @param _sleep_until the new time to sleep until.
     */
    public synchronized void restart(long _sleep_until) {
        this.sleepUntil = _sleep_until;
        notify();
    }
    
    /**
     * Stops (destroy) the thread.
     */
    public synchronized void stop() {
        shutdown = true;
        notify();
    }  
    
    
    public synchronized void run() {
        debug("running");
        while(!shutdown) { 
            try {
                // check if there's an alarm scheduled
                if (sleepUntil <= 0) {
                    // no alarm. Wait for a new alarm to come along.
                    wait();
                } // if
                else {
                    // Found alarm, set timeout based on alarm time
                    long timeout = sleepUntil - System.currentTimeMillis();
                    if (timeout > 0) {
                        wait(timeout);
                    }
                }
                
                // now that we've awakened again, check if an alarm is due (within
                // 1 second or already past)
                if (sleepUntil >= 0 && (sleepUntil - System.currentTimeMillis() < 1000)) {
                    // yes, an alarm is ready (or already past). Notify the manager to ring it.
                    sleepUntil = -1;
                    debug("notifying manager to ring next alarm");
                    mgr.ringNextAlarm();
                }
                
            }
            catch(InterruptedException e) {
                debug("interrupted");
            }
        }
        debug("stopping");
    }
    
}


