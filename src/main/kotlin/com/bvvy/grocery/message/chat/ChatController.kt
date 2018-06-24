package com.bvvy.grocery.message.chat

import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/chat")
class ChatController(val chatRepository: ChatRepository) {

    @GetMapping("/")
    fun index(): Authentication? {
        return SecurityContextHolder.getContext().authentication
    }

    @GetMapping("/{userId}/messages")
    fun userMessages(pageable: Pageable, @PathVariable userId: Int): ResponseEntity<Iterable<Chat>> {
        val chat = QChat.chat
        val predicate = chat.fromId.eq(userId)
        return ResponseEntity.ok(chatRepository.findAll(predicate, pageable))
    }

}
