package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 13.02.2023, 20:09
 */
interface Find<T> {

    fun find(id: Int): T

    interface Suspend<T> {
        suspend fun find(id: Int): T
    }
}