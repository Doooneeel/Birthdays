package ru.daniilglazkov.birthdays.data.database.converttype

/**
 * @author Danil Glazkov on 11.09.2022, 00:00
 */
interface ConvertType<T> {
    fun toType(data: String): T
    fun fromType(data: T): String
}