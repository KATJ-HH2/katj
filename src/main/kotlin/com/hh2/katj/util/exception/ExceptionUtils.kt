package com.hh2.katj.util.exception

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

fun fail(): Nothing {
    throw IllegalArgumentException()
}

fun failWithMessage(message: String): Nothing {
    throw IllegalArgumentException(message)
}


fun <T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T {
    return this.findByIdOrNull(id) ?: fail()
}


fun <T, ID> CrudRepository<T, ID>.findByIdOrThrowMessage(id: ID, message: String): T {
    return this.findByIdOrNull(id) ?: failWithMessage(message)
}