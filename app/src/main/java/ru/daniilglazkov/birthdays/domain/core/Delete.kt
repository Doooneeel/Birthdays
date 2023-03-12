package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 03.02.2023, 19:49
 */
interface Delete {

    fun delete(id: Int)

    interface Suspend {
        suspend fun delete(id: Int)
    }
}