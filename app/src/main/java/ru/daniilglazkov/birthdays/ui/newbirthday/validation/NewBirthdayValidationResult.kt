package ru.daniilglazkov.birthdays.ui.newbirthday.validation

import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.newbirthday.NewBirthdayUi

/**
 * @author Danil Glazkov on 25.02.2023, 09:32
 */
interface NewBirthdayValidationResult {

    fun <T> map(mapper: Mapper<T>): T

    interface Mapper<T> {

        fun map(birthday: NewBirthdayUi): T

        fun map(message: ErrorMessage): T

    }


    data class Valid(private val newBirthdayUi: NewBirthdayUi) : NewBirthdayValidationResult {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(newBirthdayUi)
    }

    data class Invalid(private val error: ErrorMessage) : NewBirthdayValidationResult {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(error)
    }
}