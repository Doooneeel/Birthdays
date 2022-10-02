package ru.daniilglazkov.birthdays.ui.birthdays.newbirthday

import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.core.validate.Validate

/**
 * @author Danil Glazkov on 05.09.2022, 00:25
 */
interface ValidateNewBirthday {
    fun validate(
        validate: Validate,
        successful: (NewBirthdayUi) -> Unit,
        onFailure: (ErrorMessage) -> Unit
    )
}