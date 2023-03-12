package ru.daniilglazkov.birthdays.ui.core.text.validate

import ru.daniilglazkov.birthdays.ui.core.ErrorMessage

/**
 * @author Danil Glazkov on 25.07.2022, 04:19
 */
interface Validate {

    fun isValid(text: String): Boolean

    fun errorMessage(): ErrorMessage


    abstract class Abstract(private val errorMessage: String) : Validate {
        override fun errorMessage() = ErrorMessage.Base(errorMessage)
    }
}