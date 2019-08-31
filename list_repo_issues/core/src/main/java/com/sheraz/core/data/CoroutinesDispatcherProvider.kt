package com.sheraz.core.data

import kotlinx.coroutines.CoroutineDispatcher

data class CoroutinesDispatcherProvider (
    val mainDispatcher: CoroutineDispatcher,
    val ioDispatcher: CoroutineDispatcher,
    val computationDispatcher: CoroutineDispatcher
)