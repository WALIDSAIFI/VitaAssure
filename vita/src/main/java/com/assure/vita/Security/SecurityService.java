package com.assure.vita.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    
    public boolean isCurrentUser(Long userId) {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        return currentUser.getId().equals(userId);
    }
} 