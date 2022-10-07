package ru.daniilglazkov.birthdays.ui.core.textfilter

/**
 * @author Danil Glazkov on 20.08.2022, 03:45
 */
class TextFilterChain(
    private val base: TextFilter,
    private val next: TextFilter
) : TextFilter {
    override fun filter(text: String): String =
        next.filter(base.filter(text))
}