package ru.daniilglazkov.birthdays.ui.core

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import ru.daniilglazkov.birthdays.TestCore
import ru.daniilglazkov.birthdays.ui.BaseUiTest

/**
 * @author Danil Glazkov on 11.03.2023, 18:20
 */
@ExperimentalCoroutinesApi
class HandleDomainRequestTest : BaseUiTest(), TestCore {

    @Test
    fun test_with_mapper() = runBlocking {
        val dispatchers = TestDispatchers()
        val handleResult = TestHandleWithMapper(dispatchers)

        handleResult.mapResult = 1000

        handleResult.handle(this) { "result" }

        assertEquals(1, handleResult.launchCalledList.size)
        assertEquals(handleResult.mapResult, handleResult.launchCalledList[0])

        assertEquals(1, handleResult.mapCalledList.size)
        assertEquals("result", handleResult.mapCalledList[0])

        assertCollectionEquals(
            listOf(TestDispatchers.BACKGROUND, TestDispatchers.CHANGE_TO_UI),
            dispatchers.calledList
        )
    }


    @Test
    fun test_abstract() = runBlocking {
        val dispatchers = TestDispatchers()
        val handleResult = TestHandle(dispatchers)

        handleResult.handle(this) { 1000 }

        assertEquals(1, handleResult.launchCalledList.size)
        assertEquals(1000, handleResult.launchCalledList[0])

        assertCollectionEquals(
            listOf(TestDispatchers.BACKGROUND, TestDispatchers.CHANGE_TO_UI),
            dispatchers.calledList
        )
    }


    private class TestHandleWithMapper(
        dispatchers: TestDispatchers
    ) : HandleDomainRequest.AbstractWithMapper<String, Int>(dispatchers) {

        val launchCalledList = mutableListOf<Int>()
        val mapCalledList = mutableListOf<String>()
        var mapResult = 0

        override fun launchUi(result: Int) { launchCalledList.add(result) }

        override suspend fun map(response: String): Int {
            mapCalledList.add(response)
            return mapResult
        }
    }


    private class TestHandle(
        dispatchers: TestDispatchers
    ) : HandleDomainRequest.Abstract<Int>(dispatchers) {
        val launchCalledList = mutableListOf<Int>()

        override fun launchUi(response: Int) {
            launchCalledList.add(response)
        }
    }
}