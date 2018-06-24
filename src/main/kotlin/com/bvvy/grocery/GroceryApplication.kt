package com.bvvy.grocery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer

@SpringBootApplication
@EnableResourceServer
@EnableAuthorizationServer
@EnableOAuth2Client
class GroceryApplication

fun main(args: Array<String>) {
    runApplication<GroceryApplication>(*args)
}
