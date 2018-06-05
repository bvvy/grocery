package com.bvvy.grocery.message.config;

import com.bvvy.grocery.er.Jsoner
import com.bvvy.grocery.er.Messager
import com.bvvy.grocery.message.chat.Chat
import com.bvvy.grocery.message.chat.ChatHandler
import com.bvvy.grocery.message.chat.ChatTO
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.handler.TextWebSocketHandler
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor


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


