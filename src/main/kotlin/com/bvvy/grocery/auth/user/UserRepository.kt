package com.bvvy.grocery.auth.user


import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {
    fun findByPhone(phone: String): User?
}