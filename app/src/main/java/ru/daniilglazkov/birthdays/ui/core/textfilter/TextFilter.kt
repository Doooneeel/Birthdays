package ru.daniilglazkov.birthdays.ui.core.textfilter

/**
 * @author Danil Glazkov on 20.08.2022, 03:44
 */
interface TextFilter {
    fun filter(text: String): String

    abstract class AbstractRegex(pattern: String, private val replacement: String) : TextFilter {
        private val regex = Regex(pattern)

        override fun filter(text: String): String = text.replace(regex, replacement)
    }
}