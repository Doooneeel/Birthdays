package ru.daniilglazkov.birthdays.ui.birthdays.newbirthday

import ru.daniilglazkov.birthdays.ui.core.Communication
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.core.validate.Validate

/**
 * @author Danil Glazkov on 25.08.2022, 13:40
 */
interface NewBirthdayCommunication : Communication.Mutable<NewBirthdayUi>, ValidateNewBirthday {

    class Base : Communication.UiUpdate<NewBirthdayUi>(), NewBirthdayCommunication {
        override fun validate(
            validate: Validate,
            successful: (NewBirthdayUi) -> Unit,
            onFailure: (ErrorMessage) -> Unit
        ) = value.validate(validate, successful, onFailure)
    }
}