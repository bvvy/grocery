package com.bvvy.grocery.message.chat

import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.CrudRepository

interface ChatRepository : CrudRepository<Chat, Int>, QuerydslPredicateExecutor<Chat>