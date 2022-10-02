package ru.daniilglazkov.birthdays.ui.core.validate

/**
 * @author Danil Glazkov on 25.07.2022, 04:19
 */
interface Validate {
    fun isValid(text: String): Boolean
    fun errorMessage(): String

    abstract class Abstract(private val errorMessage: String) : Validate {
        override fun errorMessage() = errorMessage
    }
}