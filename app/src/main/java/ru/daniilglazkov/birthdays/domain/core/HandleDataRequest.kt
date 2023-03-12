package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 20.02.2023, 3:53
 */
interface HandleDataRequest<T> {

    suspend fun handle(block: suspend () -> T): T


    abstract class Abstract<T> : HandleDataRequest<T> {

        protected abstract fun handleException(exception: Exception): T

        override suspend fun handle(block: suspend () -> T): T = try {
            block.invoke()
        } catch (ex: Exception) {
            handleException(ex)
        }
    }
}