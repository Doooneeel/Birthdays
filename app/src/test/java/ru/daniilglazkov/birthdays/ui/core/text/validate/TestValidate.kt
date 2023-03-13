package ru.daniilglazkov.birthdays.ui.core.text.validate

import ru.daniilglazkov.birthdays.ui.core.ErrorMessage

/**
 * @author Danil Glazkov on 08.02.2023, 21:55
 */
class TestValidate : Validate {

    lateinit var errorMessage: String

    var isValid: Boolean = false
    val isValidCalledList = mutableListOf<String>()
    var errorMessageCount = 0

    override fun errorMessage(): ErrorMessage {
        ++errorMessageCount
        return ErrorMessage.Base(errorMessage)
    }
    override fun isValid(text: String): Boolean {
        isValidCalledList.add(text)
        return isValid
    }
}