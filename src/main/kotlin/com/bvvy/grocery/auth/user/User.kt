package com.bvvy.grocery.auth.user

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "sys_user")
class User(
        @Id
        @GeneratedValue
        var id: Int = 0,

        var username: String = "",

        var password: String = "",

        var nickname: String = "",

        var phone: String = "",

        var email: String = "",

        var gender: String = "",

        val avatar: String = "",

        var enabled: Boolean = false,

        @Column(name = "create_date_time")
//    @JsonFormat(pattern = DateConst.DATETIME_FORMATTER)
        var createDateTime: LocalDateTime = LocalDateTime.now(),

        @Column(name = "update_date_time")
//    @JsonFormat(pattern = DateConst.DATETIME_FORMATTER)
        var updateDateTime: LocalDateTime = LocalDateTime.now()
)