package ru.daniilglazkov.birthdays.ui.newbirthday

import ru.daniilglazkov.birthdays.ui.core.view.AbstractView
import java.time.LocalDate

/**
 * @author Danil Glazkov on 01.09.2022, 21:52
 */
interface NewBirthdayUi {

    fun <T> map(mapper: Mapper<T>): T

    fun apply(name: AbstractView.Text, date: AbstractView.Date)

    interface Mapper<T> {
        fun map(name: String, date: LocalDate): T
    }


    abstract class Abstract(private val name: String, private val date: LocalDate) : NewBirthdayUi {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(name, date)

        override fun apply(name: AbstractView.Text, date: AbstractView.Date) {
            name.map(this.name)
            date.map(this.date)
        }
    }

    data class Base(private val name: String, private val date: LocalDate) : Abstract(name, date)

    data class Empty(private val now: LocalDate) : Abstract("", now)
}