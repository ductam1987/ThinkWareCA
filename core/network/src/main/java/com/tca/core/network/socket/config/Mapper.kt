package com.tca.core.network.socket.config

interface Mapper<T> {
    fun mapper(array: Array<out Any>): T
}
