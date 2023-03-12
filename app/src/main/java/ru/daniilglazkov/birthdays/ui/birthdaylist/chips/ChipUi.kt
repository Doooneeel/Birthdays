package ru.daniilglazkov.birthdays.ui.birthdaylist.chips

import ru.daniilglazkov.birthdays.domain.core.DeterminePosition
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 16.11.2022, 23:08
 */
interface ChipUi {

    fun <T> map(mapper: Mapper<T>): T

    fun apply(view: AbstractView.Text)

    fun position(determinePosition: DeterminePosition): Int

    interface Mapper<T> {
        fun map(id: Int, name: String): T
    }


    abstract class Abstract(private val id: Int, private val text: String) : ChipUi {

        override fun apply(view: AbstractView.Text) = view.map(text)

        override fun <T> map(mapper: Mapper<T>): T =
            mapper.map(id, text)

        override fun position(determinePosition: DeterminePosition): Int =
            determinePosition.position(id)
    }

    data class Base(private val id: Int, private val text: String) : Abstract(id, text)

    data class WithoutId(private val text: String) : Abstract(id = -1, text)
}