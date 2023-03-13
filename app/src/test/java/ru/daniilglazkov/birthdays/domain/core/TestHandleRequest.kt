package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 27.02.2023, 11:32
 */
open class TestHandleRequest<T> : HandleDataRequest<T> {

    var result: T? = null
    var callCount = 0
    val calledList: MutableList<T> = mutableListOf()
    val exceptionList = mutableListOf<Exception>()

    override suspend fun handle(block: suspend () -> T): T {
        ++callCount

        try {
            result = block.invoke()
            calledList.add(result!!)
        } catch (exception: Exception) {
            exceptionList.add(exception)
        }

        return result ?: throw IllegalStateException(
            "block threw an exception, result is not initialized"
        )
    }
}