package ru.daniilglazkov.birthdays.data.birthdays

/**
 * @author Danil Glazkov on 11.06.2022, 19:15
 */
interface BirthdayListData {
    fun <T> map(mapper: Mapper<T>): T

    class Base(private val list: List<BirthdayData>) : BirthdayListData {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(list)
    }

    interface Mapper<T> {
        fun map(list: List<BirthdayData>): T
    }
}