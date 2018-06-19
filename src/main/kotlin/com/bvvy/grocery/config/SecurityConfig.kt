package com.bvvy.grocery.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter

@Configuration
class SecurityConfig : ResourceServerConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        super.configure(http)
    }
}