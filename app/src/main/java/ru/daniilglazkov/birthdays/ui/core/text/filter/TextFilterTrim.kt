package ru.daniilglazkov.birthdays.ui.core.text.filter

/**
 * @author Danil Glazkov on 27.08.2022, 13:46
 */
class TextFilterTrim : TextFilter {
    override fun filter(text: String) = text.trim()
}