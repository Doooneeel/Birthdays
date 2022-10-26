package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 24.10.2022, 00:35
 */
interface Factory<S, R> {
    fun create(source: S): R
}