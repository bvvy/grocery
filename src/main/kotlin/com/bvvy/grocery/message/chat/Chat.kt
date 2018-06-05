package com.bvvy.grocery.message.chat

import java.time.LocalDateTime
import javax.persistence.*


/**
 * Created by bvvy on 2018/3/26.
 * com.divide2.message.chat
 */
@Entity
@Table(name = "msg_chat")
data class Chat(

        @Id
        @GeneratedValue
        val id: Int = 0,

        @Column(name = "to_id")
        val toId: Int,

        @Column(name = "from_id")
        val fromId: Int,

        @Column(name = "to_name")
        val toName: String,

        @Column(name = "from_name")
        val fromName: String,

        @Column(name = "to_avatar")
        val toAvatar: String,
        @Column(name = "from_avatar")
        val fromAvatar: String,

        val content: String,

        @Column(name = "create_date")
        val createDate: LocalDateTime = LocalDateTime.now()
)