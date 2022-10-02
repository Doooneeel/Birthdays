package ru.daniilglazkov.birthdays.ui.birthdays

import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 29.09.2022, 23:06
 */
interface AboutBirthdate {
    fun apply(age: AbstractView.Text, until: AbstractView.Text)

    class Base(private val age: String, private val until: String) : AboutBirthdate {

        constructor(age: Int, until: Int) : this(age.toString(), until.toString())

        override fun apply(age: AbstractView.Text, until: AbstractView.Text) {
            age.map(this.age)
            until.map(this.until)
        }
    }

}