package ru.daniilglazkov.birthdays.ui.birthdays

/**
 * @author Danil Glazkov on 19.09.2022, 00:29
 */
interface BirthdayChipTextFormat {
    fun format(name: String, count: Int): String

    class Total : BirthdayChipTextFormat {
        override fun format(name: String, count: Int): String = "$name ($count)"
    }

    class NameAndQuantity : BirthdayChipTextFormat {
        override fun format(name: String, count: Int): String =
            if (count > 1) "$name ($count)" else name
    }
    class OnlyName : BirthdayChipTextFormat {
        override fun format(name: String, count: Int): String = name
    }
}