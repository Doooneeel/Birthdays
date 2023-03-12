package ru.daniilglazkov.birthdays.ui.newbirthday.validation

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.ui.core.resources.ManageResources
import ru.daniilglazkov.birthdays.ui.core.text.validate.*

/**
 * @author Danil Glazkov on 10.11.2022, 13:26
 */
class ValidateName(manageResources: ManageResources) : ValidateChain(
    ValidateNotEmpty(
        manageResources.string(R.string.empty_name_error_message)
    ),
    ValidateChain(
        ValidateFirstCharIsLetter(
            manageResources.string(R.string.first_char_is_not_letter_error_message)
        ),
        ValidateMinLength(
            manageResources.number(R.integer.name_min_length),
            manageResources.string(R.string.name_min_length_error_message)
        )
    )
)