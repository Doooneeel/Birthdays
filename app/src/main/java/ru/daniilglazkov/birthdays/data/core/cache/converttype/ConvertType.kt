package ru.daniilglazkov.birthdays.data.core.cache.converttype

/**
 * @author Danil Glazkov on 11.09.2022, 00:00
 */
interface ConvertType<T : Any> {
    fun to(data: String): T
    fun from(data: T): String
}