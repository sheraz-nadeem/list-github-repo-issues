package com.sheraz.core.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sheraz.core.utils.Logger
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.Rule

open class BaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    protected val logger = mockk<Logger>()

    @Before
    fun setMeUp() {

        mockkStatic(Logger::class)
        every { logger.v(any(), any()) } returns Unit
        every { logger.d(any(), any()) } returns Unit
        every { logger.i(any(), any()) } returns Unit
        every { logger.w(any(), any()) } returns Unit
        every { logger.e(any(), any()) } returns Unit

    }

}