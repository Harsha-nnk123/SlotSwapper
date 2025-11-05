package com.project.SlotSwapper.app.util;

import java.time.Instant;

public class TimeUtils { 
    public static void requireStartBeforeEnd(Instant start, Instant end){ 
        if(start.isAfter(end) || start.equals(end)) 
        throw new IllegalArgumentException("startTime must be before endTime"); 
    } 
}
