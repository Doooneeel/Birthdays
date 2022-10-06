package ru.daniilglazkov.birthdays.ui.birthdays.newbirthday

import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayDomain
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.core.validate.Validate
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView
import java.time.LocalDate

/**
 * @author Danil Glazkov on 01.09.2022, 21:52
 */
interface NewBirthdayUi : ValidateNewBirthday {
    fun <T> map(mapper: Mapper<T>): T
    fun apply(name: AbstractView.Text, date: AbstractView.Date)

    abstract class Abstract(
        private val name: String,
        private val date: LocalDate
    ) : NewBirthdayUi {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(name, date)
        override fun apply(name: AbstractView.Text, date: AbstractView.Date) {
            name.map(this.name)
            date.map(this.date)
        }
        override fun validate(
            validate: Validate,
            successful: (NewBirthdayUi) -> Unit,
            onFailure: (ErrorMessage) -> Unit
        ) {
            if (validate.isValid(name)) {
                successful.invoke(this)
            } else {
                onFailure.invoke(
                    ErrorMessage.Base(validate.errorMessage())
                )
            }
        }
    }
    class Base(name: String, date: LocalDate) : Abstract(name, date)
    object Empty : Abstract("", LocalDate.now())

    interface Mapper<T> {
        fun map(name: String, date: LocalDate): T

        class ToDomain : Mapper<NewBirthdayDomain> {
            override fun map(name: String, date: LocalDate) = NewBirthdayDomain.Base(name, date)
        }
    }

}