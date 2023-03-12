package ru.daniilglazkov.birthdays.ui.birthday

import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 24.02.2023, 4:30
 */
interface DaysLeft {

    fun apply(left: AbstractView.Text, days: AbstractView.Text)


    data class Base(private val left: String, private val days: String) : DaysLeft {
        override fun apply(left: AbstractView.Text, days: AbstractView.Text) {
            left.map(this.left)
            days.map(this.days)
        }
    }
}