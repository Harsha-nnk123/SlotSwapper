package com.project.SlotSwapper.app.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUser {
    public static Long id(){
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if(a == null) return null;
        try { 
            return Long.valueOf(a.getName()); 
        } catch (Exception e){ 
            return null; 
        }
    }
}