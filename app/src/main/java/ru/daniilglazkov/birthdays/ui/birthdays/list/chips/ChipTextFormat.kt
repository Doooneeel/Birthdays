package ru.daniilglazkov.birthdays.ui.birthdays.list.chips

/**
 * @author Danil Glazkov on 19.09.2022, 00:29
 */
interface ChipTextFormat {
    fun format(title: String, count: Int): String

    class NameWithCount : ChipTextFormat {
        override fun format(title: String, count: Int): String = "$title: $count"
    }
    class OnlyName : ChipTextFormat {
        override fun format(title: String, count: Int): String = title
    }
}