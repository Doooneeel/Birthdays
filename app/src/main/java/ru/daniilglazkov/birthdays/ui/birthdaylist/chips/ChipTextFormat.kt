package ru.daniilglazkov.birthdays.ui.birthdaylist.chips

/**
 * @author Danil Glazkov on 19.09.2022, 00:29
 */
interface ChipTextFormat {
    fun format(title: String, count: Int): String

    class NameWithCount : ChipTextFormat {
        override fun format(title: String, count: Int) = "$title: $count"
    }
    class OnlyName : ChipTextFormat {
        override fun format(title: String, count: Int) = title
    }
}