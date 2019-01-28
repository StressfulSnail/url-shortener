package com.stressfulsnail.urlshortener.config

import com.stressfulsnail.urlshortener.model.UserEntity
import com.stressfulsnail.urlshortener.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
        if (!user) {
            return null
        }

        Set<String> roles = user.roles.collect { it.role }
        return new UserDetailsImpl(user.username, user.password, roles)
    }
}
