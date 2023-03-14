package ru.shipownerproject.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.shipownerproject.models.user.Role;
import ru.shipownerproject.models.user.User;

import java.util.Collection;
import java.util.Collections;

public record UserWrapper(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isNonCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
