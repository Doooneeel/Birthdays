package ru.daniilglazkov.birthdays.ui.birthdays.showmode

import ru.daniilglazkov.birthdays.core.Mapper

/**
 * @author Danil Glazkov on 11.08.2022, 05:10
 */
interface BirthdaysChips {
    fun apply(mapper: Mapper.Unit<List<String>>)

    abstract class Abstract(private val chips: List<String>) : BirthdaysChips {
        override fun apply(mapper: Mapper.Unit<List<String>>) = mapper.map(chips)
    }

    class Base(chips: List<String>) : Abstract(chips)
    class Empty : Abstract(emptyList())
}
