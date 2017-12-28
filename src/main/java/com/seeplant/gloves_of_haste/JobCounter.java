package com.seeplant.gloves_of_haste;

import java.util.concurrent.atomic.AtomicLong;

public class JobCounter {
    private static AtomicLong counter;
    static {
        counter = new AtomicLong();
    }
    
    public static long incrementAndGet() {
        return counter.incrementAndGet();
    }
    
    public static long decrementAndGet() {
        return counter.decrementAndGet();
    }
    
    public static long get() {
        return counter.get();
    }
}
