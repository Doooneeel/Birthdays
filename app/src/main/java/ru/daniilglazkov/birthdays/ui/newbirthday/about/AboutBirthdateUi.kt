package ru.daniilglazkov.birthdays.ui.newbirthday.about

import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 29.09.2022, 23:06
 */
interface AboutBirthdateUi {
    fun apply(turnedYears: AbstractView.Text, daysToBirthday: AbstractView.Text)

    class Base(
        private val turnedYear: String,
        private val daysToBirthday: String,
    ) : AboutBirthdateUi {
        override fun apply(turnedYears: AbstractView.Text, daysToBirthday: AbstractView.Text) {
            turnedYears.map(this.turnedYear)
            daysToBirthday.map(this.daysToBirthday)
        }
    }
}