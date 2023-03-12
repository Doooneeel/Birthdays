package ru.daniilglazkov.birthdays.ui.core.text.validate

/**
 * @author Danil Glazkov on 06.08.2022, 01:41
 */
class ValidateFirstCharIsLetter(message: String): Validate.Abstract(message) {
    override fun isValid(text: String): Boolean = text.isNotBlank() && text[0].isLetter()
}