package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 27.06.2022, 11:43
 */
interface Add<T : Any> {
    fun add(data: T)
}