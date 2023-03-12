package ru.daniilglazkov.birthdays.ui.core.text.validate

/**
 * @author Danil Glazkov on 25.07.2022, 04:17
 */
class ValidateMinLength(private val minLength: Int, message: String) : Validate.Abstract(message) {
    override fun isValid(text: String) = text.length >= minLength
}
