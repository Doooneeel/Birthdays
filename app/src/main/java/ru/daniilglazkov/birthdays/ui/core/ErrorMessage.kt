package ru.daniilglazkov.birthdays.ui.core

import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 25.08.2022, 14:35
 */
interface ErrorMessage {
    fun apply(view: AbstractView.Text)

    abstract class Abstract(private val error: String) : ErrorMessage {
        override fun apply(view: AbstractView.Text) = view.map(error)
    }

    class Base(error: String) : Abstract(error)

    object Empty : Abstract("")
}