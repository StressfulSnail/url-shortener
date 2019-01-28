package com.stressfulsnail.urlshortener.config

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl implements UserDetails {
    private Set<GrantedAuthorityImpl> authorities
    private String username
    private String password

    UserDetailsImpl(String username, String password, Set<String> roles) {
        this.username = username
        this.password = password
        this.authorities = roles.collect { role -> new GrantedAuthorityImpl(authority: role) }
    }

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        // clone set
        return authorities.collect()
    }

    @Override
    String getPassword() {
        return password
    }

    @Override
    String getUsername() {
        return username
    }

    /**
     * Not currently using, always return true.
     * @return true
     */
    @Override
    boolean isAccountNonExpired() {
        return true
    }

    /**
     * Not currently using, always return true.
     * @return true
     */
    @Override
    boolean isAccountNonLocked() {
        return true
    }

    /**
     * Not currently using, always return true.
     * @return true
     */
    @Override
    boolean isCredentialsNonExpired() {
        return true
    }

    /**
     * Not currently using, always return true.
     * @return true
     */
    @Override
    boolean isEnabled() {
        return true
    }
}
