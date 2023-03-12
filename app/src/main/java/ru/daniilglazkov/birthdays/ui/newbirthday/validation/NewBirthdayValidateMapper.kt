package ru.daniilglazkov.birthdays.ui.newbirthday.validation

import ru.daniilglazkov.birthdays.ui.core.text.validate.Validate
import ru.daniilglazkov.birthdays.ui.newbirthday.NewBirthdayUi
import java.time.LocalDate

/**
 * @author Danil Glazkov on 25.02.2023, 09:23
 */
interface NewBirthdayValidateMapper : NewBirthdayUi.Mapper<NewBirthdayValidationResult> {

    data class Base(private val validate: Validate) : NewBirthdayValidateMapper {
        override fun map(name: String, date: LocalDate): NewBirthdayValidationResult {
            return if (validate.isValid(name)) {
                NewBirthdayValidationResult.Valid(NewBirthdayUi.Base(name, date))
            } else {
                NewBirthdayValidationResult.Invalid(validate.errorMessage())
            }
        }
    }
}