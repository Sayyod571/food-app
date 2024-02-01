package org.example.cookieretceptg27.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextHolderService {
    public static String getUserFromSecurityContextHolder(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
         return authentication.getName();
    }
}

