package com.bvvy.grocery.auth.oauth

import com.bvvy.grocery.er.Messager
import com.bvvy.grocery.er.Responser
import org.omg.CORBA.Object
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.client.postForObject
import java.util.*

data class LoginUrl(val url: String)

val clientId = "1c23891103858487501e"
val clientSecret = "f33376b3bdf95b4c4ef2645b0358b183af8afb4"
val redirectUri = ""
val authorizationUrl = "https://github.com/login/oauth/authorize"
val accessUrl = "https://github.com/login/oauth/access_token"
val userinfoUrl = "https://api.github.com/user"

@Controller
@RequestMapping("/login/oauth")
class LoginController(restTemplateBuilder: RestTemplateBuilder) {

    val restTemplate = restTemplateBuilder.build()

    @ResponseBody
    @GetMapping("/url/github")
    fun githubLoginUrl(): ResponseEntity<LoginUrl> {
        val url = "https://github.com/login/oauth/authorize?client_id=$clientId&client_secrect=$clientSecret&state=${Base64StringKeyGenerator().generateKey()}"
        return Responser.ok(LoginUrl(url))
    }

    @GetMapping("/code/github")
    fun githubLoginCode(callbackData: CallbackData): String {
        restTemplate.postForObject<Object>("https://github.com/login/oauth/access_token", null)
        return "redirect:http://127.0.0.1:8080/#/login/callback?code=${callbackData.code}&state=${callbackData.state}"
    }

}

data class CallbackData(val code: String, val state: String)
