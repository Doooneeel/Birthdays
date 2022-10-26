package ru.daniilglazkov.birthdays.ui.birthday

import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 01.09.2022, 21:25
 */
interface BirthdayUi {

    fun apply(
        nameView: AbstractView.Text,
        dateView: AbstractView.Text,
        turnedYearsView: AbstractView.Text,
        daysToBirthdayView: AbstractView.Text
    )


    class Base(
        private val name: String,
        private val date: String,
        private val turnsYearsOld: String,
        private val daysToBirthday: String
    ) : BirthdayUi {
        override fun apply(
            nameView: AbstractView.Text,
            dateView: AbstractView.Text,
            turnedYearsView: AbstractView.Text,
            daysToBirthdayView: AbstractView.Text
        ) {
            nameView.map(name)
            dateView.map(date)
            turnedYearsView.map(turnsYearsOld)
            daysToBirthdayView.map(daysToBirthday)
        }
    }
}