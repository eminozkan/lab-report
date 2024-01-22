package dev.ozkan.labreport.model.user;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserDetails sınıfından kalıtım yapan ve User sınıfında bulunmayacak olan isAccountNonExpired, isAccountNonLocked ve isCredentialsNonExpired methodları
 * için varsayılan değerleri tutan bir sınıftır.
 */
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
