package ru.daniilglazkov.birthdays.ui.newbirthday.about

import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 29.09.2022, 23:06
 */
interface AboutBirthdateUi {

    fun apply(turnsYearsOld: AbstractView.Text, daysToBirthday: AbstractView.Text)


    data class Base(
        private val turnsYearsOld: String,
        private val daysToBirthday: String,
    ) : AboutBirthdateUi {
        override fun apply(turnsYearsOld: AbstractView.Text, daysToBirthday: AbstractView.Text) {
            turnsYearsOld.map(this.turnsYearsOld)
            daysToBirthday.map(this.daysToBirthday)
        }
    }
}