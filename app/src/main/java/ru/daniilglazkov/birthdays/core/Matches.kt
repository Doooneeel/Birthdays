package ru.daniilglazkov.birthdays.core

/**
 * @author Danil Glazkov on 19.06.2022, 12:29
 */
interface Matches<T> {
    fun matches(data: T): Boolean
}