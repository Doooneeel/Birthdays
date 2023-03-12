package ru.daniilglazkov.birthdays.ui.core.text.validate

/**
 * @author Danil Glazkov on 25.07.2022, 09:38
 */
class ValidateNotEmpty(message: String) : Validate.Abstract(message) {
    override fun isValid(text: String) = text.isNotEmpty() && text.isNotBlank()
}