package ru.daniilglazkov.birthdays.ui.core.validate

/**
 * @author Danil Glazkov on 25.07.2022, 04:21
 */
class ValidateChain(
    private val base: Validate,
    private val next: Validate
) : Validate {
    private var baseValid = false

    override fun isValid(text: String): Boolean {
        baseValid = base.isValid(text)
        return if (baseValid) next.isValid(text) else false
    }

    override fun errorMessage() = if (baseValid) next.errorMessage() else base.errorMessage()
}
