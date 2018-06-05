package com.bvvy.grocery.message.chat

import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/chat")
class ChatController(val chatRepository: ChatRepository) {

    @RequestMapping("/{userId}/messages")
    fun userMessages(pageable: Pageable):ResponseEntity<Iterable<Chat>> {
        return ResponseEntity.ok(chatRepository.findAll())
    }

}
