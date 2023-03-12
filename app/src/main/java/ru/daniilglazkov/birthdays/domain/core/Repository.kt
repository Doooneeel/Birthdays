package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 21.02.2023, 23:50
 */
interface Repository {

    interface Save<T> {
        suspend fun save(data: T)
    }

    interface Read<T> {
        suspend fun read(): T
    }

    interface Mutable<T> : Save<T>, Read<T>
}