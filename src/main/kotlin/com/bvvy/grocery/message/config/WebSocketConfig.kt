package com.bvvy.grocery.message.config;

import com.bvvy.grocery.message.chat.ChatHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry


/**
 * Created by bvvy on 2018/3/26.
 * com.divide2.sms.config
 */

@Configuration
@EnableWebSocket
class WebSocketConfig(val chatHandler: ChatHandler) : WebSocketConfigurer {


    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(chatHandler, "/myHandler")
                .setAllowedOrigins("http://localhost:8080")
    }

}


