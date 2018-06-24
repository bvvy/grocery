package com.bvvy.grocery.core.er

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

/**
 * Created by bvvy on 2018/1/9.
 * com.divide2.constant
 */
object Responser {

    private const val UPDATE_STATUE = 209
    private const val DELETE_STATUS = 210


    fun <T> ok(body: T): ResponseEntity<T> {
        return ResponseEntity.ok(body)
    }

    fun created(): ResponseEntity<Messager> {
        return ResponseEntity.status(HttpStatus.CREATED).body(Messager("m_add_success"))
    }

    fun update(): ResponseEntity<Messager> {
        return ResponseEntity.status(UPDATE_STATUE).body(Messager("m_update_success"))
    }

    fun delete(): ResponseEntity<Messager> {
        return ResponseEntity.status(DELETE_STATUS).body(Messager("m_delete_success"))
    }

    fun conflict(code: String): ResponseEntity<Messager> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Messager(code))
    }
}