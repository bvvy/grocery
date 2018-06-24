package com.bvvy.grocery.auth.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter

@Configuration
class ResourceConfig : ResourceServerConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http
                .antMatcher("/user")
                .authorizeRequests()
                .antMatchers("/v1/join").permitAll()
                .anyRequest().authenticated()
    }
}