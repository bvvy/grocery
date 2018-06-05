package com.bvvy.grocery.message.chat

import com.bvvy.grocery.er.Jsoner
import com.bvvy.grocery.er.Messager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler


/**
 * Created by bvvy on 2018/3/26.
 * com.divide2.message.chat
 */
@Controller
class ChatHandler : TextWebSocketHandler() {

    @Autowired
    lateinit var  chatRepository: ChatRepository

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val chatDO = Jsoner.fromJson<ChatTO>(message.payload)
        val chat = Chat(
                toId = chatDO.toId,
                fromId = chatDO.fromId,
                content = chatDO.content,
                fromName = "jack",
                toName = "rose",
                fromAvatar = "jackavatar.png",
                toAvatar = "roseavatar.png"
        )
        chatRepository.save(chat)
        session.sendMessage(TextMessage(Jsoner.toJson(Messager("send_success"))))
    }

}