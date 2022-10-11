package ru.daniilglazkov.birthdays.domain.core

import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException

/**
 * @author Danil Glazkov on 10.10.2022, 17:31
 */
interface HandleRepositoryResponse<T> {

    fun handle(block: () -> T) : T


    abstract class Abstract<T>(private val defaultValue: T) : HandleRepositoryResponse<T> {
        override fun handle(block: () -> T): T = try {
            block.invoke()
        } catch (emptyCacheException: EmptyCacheException) {
            defaultValue
        } catch (unknownException: Exception) {
            unknownException.printStackTrace()
            defaultValue
        }
    }
}