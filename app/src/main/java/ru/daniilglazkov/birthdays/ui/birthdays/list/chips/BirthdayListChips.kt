package ru.daniilglazkov.birthdays.ui.birthdays.list.chips

import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 11.08.2022, 05:10
 */
interface BirthdayListChips {
    fun apply(view: AbstractView.List<String>)

    abstract class Abstract(private val chips: List<String>) : BirthdayListChips {
        override fun apply(view: AbstractView.List<String>) = view.map(chips)
    }
    class Base(chips: List<String>) : Abstract(chips)
    class Empty : Abstract(emptyList())
}
