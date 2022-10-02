package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 22.09.2022, 20:20
 */
interface Concatenate<T> {
    fun concatenate(data: List<T>): T
}