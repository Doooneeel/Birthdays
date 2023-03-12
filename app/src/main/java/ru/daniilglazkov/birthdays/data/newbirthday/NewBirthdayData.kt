package ru.daniilglazkov.birthdays.data.newbirthday

/**
 * @author Danil Glazkov on 06.09.2022, 01:12
 */
interface NewBirthdayData {

    fun <T> map(mapper: Mapper<T>): T

    interface Mapper<T> {
        fun map(name: String, epochDay: Long): T
    }


    data class Base(private val name: String, private val epochDay: Long) : NewBirthdayData {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(name, epochDay)
    }
}