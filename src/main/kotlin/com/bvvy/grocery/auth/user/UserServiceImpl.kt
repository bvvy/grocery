package com.bvvy.grocery.auth.user

import com.bvvy.grocery.exception.AlreadyExistsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(val userRepository: UserRepository,val passwordEncoder: PasswordEncoder) : UserService {

    override fun save(user: User) {
        val existsUser: User? = loadByPhone(user.phone)
        if (existsUser != null) {
            throw AlreadyExistsException()
        }
        user.password = passwordEncoder.encode(user.password)
        userRepository.save(user)
    }

    override fun oauthSave(user: User) {
        userRepository.save(user)
    }

    override fun loadByPhone(phone: String): User? {
        return userRepository.findByPhone(phone)
    }

}