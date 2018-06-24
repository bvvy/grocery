package com.bvvy.grocery.auth.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer


/**
 * @author bvvy
 * @date 2018/6/12
 */
@Configuration
class OAuth2Config(val authenticationManager: AuthenticationManager,
                   val userDetailsService: UserDetailsService) : AuthorizationServerConfigurerAdapter() {

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
                .withClient("eagleeye")
                .secret("\$2a\$10\$.ifhoEH6RplL9lUfxb3V/OOs36OLda8KjGPfjPwp0hVnvZJMORfRa")
                .authorizedGrantTypes("refresh_token", "password", "client_credentials")
                .scopes("webclient", "mobileclient")
    }


    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
    }
}