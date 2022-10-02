package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 07.08.2022, 17:15
 */
interface Transform<S, R> {
    fun transform(data: S): R

    interface Single<T> : Transform<T, T>
}