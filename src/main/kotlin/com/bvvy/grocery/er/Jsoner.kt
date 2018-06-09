package com.bvvy.grocery.er

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

object Jsoner {

    var mapper = jacksonObjectMapper()

    fun toJson(obj: Any): String = mapper.writeValueAsString(obj)

    inline fun <reified T : Any> fromJson(json: String): T = mapper.readValue(json)

}