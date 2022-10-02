package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 08.08.2022, 19:28
 */
interface Find<T> {
    fun find(id: Int): T
}