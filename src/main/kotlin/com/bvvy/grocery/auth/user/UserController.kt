package com.bvvy.grocery.auth.user

import com.bvvy.grocery.er.Messager
import com.bvvy.grocery.er.Responser
import org.hibernate.validator.constraints.Length
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import javax.validation.constraints.NotEmpty


@RestController
class UserController(val userService: UserService) {

    @GetMapping("/user")
    fun user(user: Principal): Principal {
        return user
    }

    @PostMapping("/v1/join")
    fun join(@RequestBody joinDto: JoinDto): ResponseEntity<Messager> {

        val user = User(
                password = joinDto.password,
                nickname = joinDto.account,
                phone = joinDto.account,
                username = joinDto.account,
                enabled = true)
        userService.save(user)
        return Responser.created()
    }

}


data class JoinDto(
        @NotEmpty
        val account: String,

        @NotEmpty
        @Length(min = 8, max = 30)
        val password: String

)