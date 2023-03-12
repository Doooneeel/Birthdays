package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 27.06.2022, 11:43
 */
interface Add<T> {

    fun add(data: T)

    interface Suspend<T> {
        suspend fun add(data: T)
    }
}