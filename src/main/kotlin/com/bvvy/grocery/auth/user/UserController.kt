package com.bvvy.grocery.auth.user

import org.hibernate.validator.constraints.Length
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.constraints.NotEmpty


@RestController
class UserController(val userService: UserService) {

    @RequestMapping(value = ["/user"])
    fun user(user: OAuth2Authentication): Map<String, Any> {
        val userInfo = HashMap<String, Any>()
        userInfo["user"] = user.userAuthentication.principal
        userInfo["authorities"] = AuthorityUtils.authorityListToSet(user.userAuthentication.authorities)
        return userInfo
    }

    @PostMapping("/v1/join")
    fun join(@RequestBody joinDto: JoinDto) {

        val user = User(
                password = joinDto.password,
                nickname = joinDto.account,
                phone = joinDto.account,
                username = joinDto.account,
                enabled = true)
        userService.save(user)
    }

}


data class JoinDto(
        @NotEmpty
        val account: String,

        @NotEmpty
        @Length(min = 8, max = 30)
        val password: String

)