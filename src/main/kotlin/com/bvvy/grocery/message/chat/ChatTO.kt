package com.bvvy.grocery.message.chat


/**
 * Created by bvvy on 2018/3/26.
 * com.divide2.message.chat
 */
data class ChatTO(
        var toId: Int,
        var fromId: Int,
        var content: String
)