package ru.daniilglazkov.birthdays.core

/**
 * @author Danil Glazkov on 05.09.2022, 20:51
 */
interface ReadWithDefault<T> {
    fun read(default: T): T
}