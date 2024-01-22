package dev.ozkan.labreport.model.user;

import org.springframework.security.core.userdetails.UserDetails;

public abstract class CustomUserDetails implements UserDetails {
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}
