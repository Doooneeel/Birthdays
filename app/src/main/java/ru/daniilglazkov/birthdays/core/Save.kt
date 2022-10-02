package ru.daniilglazkov.birthdays.core

/**
 * @author Danil Glazkov on 10.06.2022, 22:06
 */
interface Save<T> {
    fun save(data: T)
}