package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 22.07.2022, 04:35
 */
interface Split<S, R> {
    fun split(data: S): R

    interface Single<T> : Split<T, T>
}