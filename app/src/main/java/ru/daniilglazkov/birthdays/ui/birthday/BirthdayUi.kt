package ru.daniilglazkov.birthdays.ui.birthday

import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 01.09.2022, 21:25
 */
interface BirthdayUi {

    fun apply(
        nameView: AbstractView.Text,
        birthdateView: AbstractView.Text,
        birthdayView: AbstractView.Text,
        turnsYearsOldView: AbstractView.Text,
        daysLeftViews: Pair<AbstractView.Text, AbstractView.Text>
    )

    class Base(
        private val name: String,
        private val birthdate: String,
        private val birthday: String,
        private val turnsYearsOld: String,
        private val daysLeft: Pair<String, String>
    ) : BirthdayUi {
        override fun apply(
            nameView: AbstractView.Text,
            birthdateView: AbstractView.Text,
            birthdayView: AbstractView.Text,
            turnsYearsOldView: AbstractView.Text,
            daysLeftViews: Pair<AbstractView.Text, AbstractView.Text>
        ) {
            nameView.map(name)
            birthdateView.map(birthdate)
            birthdayView.map(birthday)
            turnsYearsOldView.map(this.turnsYearsOld)

            daysLeftViews.first.map(daysLeft.first)
            daysLeftViews.second.map(daysLeft.second)
        }
    }
}