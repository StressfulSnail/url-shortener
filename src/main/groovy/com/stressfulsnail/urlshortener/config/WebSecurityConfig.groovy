package com.stressfulsnail.urlshortener.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder()
    }

    @Override
    protected void configure(HttpSecurity http) {
        http
            .httpBasic()
                .and()
            .authorizeRequests()
                .antMatchers('/api/user', '/api/user/**').hasAuthority('ADMIN')
                .antMatchers('/api/**').hasAnyAuthority('USER')
                .anyRequest().permitAll()
            .and()
        .formLogin()
            .and()
        .csrf().disable()
    }

    @Autowired
    void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder())
    }
}
