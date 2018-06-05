package com.bvvy.grocery.auth.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import javax.sql.DataSource

@Configuration
class LoginConfig(val dataSource: DataSource) : WebSecurityConfigurerAdapter() {


    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .httpBasic()
                .and()
                .logout().and()
                .authorizeRequests()
                .antMatchers("/gs-guide-websocket/**", "/", "/login/**",
                        "/v1/label/**", "/swagger*/**", "/webjars/**", "/v2/api-docs", "/v1/chat/**", "/v1/daily/**", "/myHandler").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
    }
}
