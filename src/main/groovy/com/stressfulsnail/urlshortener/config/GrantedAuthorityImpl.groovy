package com.stressfulsnail.urlshortener.config

import org.springframework.security.core.GrantedAuthority

class GrantedAuthorityImpl implements GrantedAuthority {
    String authority

    @Override
    String getAuthority() {
        return authority
    }
}
