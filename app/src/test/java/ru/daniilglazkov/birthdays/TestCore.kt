package ru.daniilglazkov.birthdays

import kotlinx.coroutines.runBlocking
import org.junit.Assert

/**
 * @author Danil Glazkov on 27.02.2023, 10:27
 */
interface TestCore {

    fun <T : Throwable> assertThrowInBlock(throwable: Class<T>, block: suspend () -> Any) {
        Assert.assertThrows(throwable) {
            runBlocking { block.invoke() }
        }
    }

    fun assertCollectionEquals(expected: Collection<Any>, actual: Collection<Any>) =
        Assert.assertArrayEquals(expected.toTypedArray(), actual.toTypedArray())
}