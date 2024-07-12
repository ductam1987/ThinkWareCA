package com.tca.core.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

interface CoroutineDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val socket: CoroutineDispatcher
}

class DefaultCoroutineDispatchers : CoroutineDispatchers {
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val default: CoroutineDispatcher = Dispatchers.Default

    @OptIn(DelicateCoroutinesApi::class)
    override val socket: CoroutineDispatcher = newSingleThreadContext(name = "SocketManager")
}
