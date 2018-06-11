package com.bvvy.grocery.auth.oauth

import com.bvvy.grocery.auth.user.User
import com.bvvy.grocery.auth.user.UserRepository
import com.bvvy.grocery.auth.user.UserService
import com.bvvy.grocery.er.Responser
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.annotation.Order
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator
import org.springframework.stereotype.Controller
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.client.getForObject
import org.springframework.web.client.postForObject


data class LoginUrl(val url: String)

val clientId = "1c23891103858487501e"
val clientSecret = "f33376b3bdf95b4c4ef2645b0358b183af8afb42"
//val redirectUri = "http://127.0.0.1:11111/login/oauth/code/github"
val authorizationUrl = "https://github.com/login/oauth/authorize"
val accessUrl = "https://github.com/login/oauth/access_token"
val userinfoUrl = "https://api.github.com/user"
val redirectFrontUrl=  "http://192.168.27.192:8080/#/login/callback"

@Controller
@RequestMapping("/login/oauth")
class LoginController(restTemplateBuilder: RestTemplateBuilder, val userService: UserService) {

    val restTemplate = restTemplateBuilder.build()

    @ResponseBody
    @GetMapping("/url/github")
    fun githubLoginUrl(): ResponseEntity<LoginUrl> {
        val url = "$authorizationUrl?client_id=$clientId&client_secret=$clientSecret&state=${Base64StringKeyGenerator().generateKey()}"
        return Responser.ok(LoginUrl(url))
    }

    @GetMapping("/code/github")
    fun githubLoginCode(callbackData: AuthCallbackData): String {
        val headers = HttpHeaders()
        headers.add("Accept", "application/json")
        val params = LinkedMultiValueMap<String, String>()
        params.add("client_id", clientId)
        params.add("client_secret", clientSecret)
        params.add("code", callbackData.code)
        params.add("state", callbackData.state)
//        params.add("redirect_uri", "$redirectUri/access")
        val requestEntity = HttpEntity<MultiValueMap<String, String>>(params, headers)
        val accessData = restTemplate.postForObject<AccessCallbackData>(accessUrl, requestEntity)
        this.getUserInfo(accessData)
        return "redirect:$redirectFrontUrl?token=${accessData?.access_token}"
    }

    private fun getUserInfo(accessData: AccessCallbackData?) {
        val githubUser = restTemplate.getForObject<GithubUser>("$userinfoUrl?access_token=${accessData?.access_token}")
//        val user = User(id = 0, username = githubUser?.login!!, email = githubUser.email, nickname = githubUser.name, avatar = githubUser.avatar_url)
//        userService.oauthSave(user)
        println(githubUser)
    }


}

data class GithubUser(val login: String,
                      val id: String,
                      val node_id: String,
                      val avatar_url: String,
                      val gravatar_id: String,
                      val url: String,
                      val html_url: String,
                      val received_events_url: String,
                      val type: String,
                      val name: String,
                      val blog: String,
                      val location: String,
                      val email: String,
                      val created_at: String,
                      val updated_at: String
)

data class AuthCallbackData(val code: String, val state: String)

data class AccessCallbackData(val access_token: String, val scope: String, val token_type: String)

