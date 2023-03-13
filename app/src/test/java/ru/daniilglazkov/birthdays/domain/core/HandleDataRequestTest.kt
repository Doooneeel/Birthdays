package ru.daniilglazkov.birthdays.domain.core

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

/**
 * @author Danil Glazkov on 06.03.2023, 16:17
 */
class HandleDataRequestTest {

    @Test
    fun test_success() = runBlocking {
        val handleDataRequest = TestHandleDataRequest()

        assertEquals(123, handleDataRequest.handle { 123 })

        assertEquals(0, handleDataRequest.handleExceptionCalledList.size)
    }

    @Test
    fun test_exception() = runBlocking {
        val exception = IllegalStateException()
        val handleDataRequest = TestHandleDataRequest()

        handleDataRequest.result = 1000

        assertEquals(1000, handleDataRequest.handle { throw exception })

        assertEquals(1, handleDataRequest.handleExceptionCalledList.size)
        assertEquals(exception, handleDataRequest.handleExceptionCalledList[0])
    }


    private class TestHandleDataRequest : HandleDataRequest.Abstract<Int>() {
        val handleExceptionCalledList = mutableListOf<Exception>()
        var result: Int = 0

        override fun handleException(exception: Exception): Int {
            handleExceptionCalledList.add(exception)
            return result
        }
    }
}