package ru.daniilglazkov.birthdays.ui.core.text.filter

/**
* @author Danil Glazkov on 20.08.2022, 03:57
*/
class TextFilterWhitespaces : TextFilter.AbstractRegex(
    pattern = "\\s+",
    replacement = " "
)