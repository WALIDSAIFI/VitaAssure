package com.assure.vita.Security;

import com.assure.vita.Entity.Utilisateur;
import com.assure.vita.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private Role role;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(Utilisateur utilisateur) {
        List<GrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().name())
        );

        return new UserPrincipal(
            utilisateur.getId(),
            utilisateur.getEmail(),
            utilisateur.getPassword(),
            utilisateur.getRole(),
            authorities
        );
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
} 