package com.sheraz.core.data

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CoroutinesDispatcherProvider(
    val mainDispatcher: CoroutineDispatcher,
    val ioDispatcher: CoroutineDispatcher,
    val computationDispatcher: CoroutineDispatcher
)