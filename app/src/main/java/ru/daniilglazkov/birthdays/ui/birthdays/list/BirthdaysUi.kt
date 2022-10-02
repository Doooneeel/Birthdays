package ru.daniilglazkov.birthdays.ui.birthdays.list

import ru.daniilglazkov.birthdays.core.Mapper
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayUi

/**
 * @author Danil Glazkov on 10.06.2022, 16:11
 */
interface BirthdaysUi {
    fun apply(mapper: Mapper.Unit<List<BirthdayUi>>)

    class Base(private val list: List<BirthdayUi>) : BirthdaysUi {
        override fun apply(mapper: Mapper.Unit<List<BirthdayUi>>) = mapper.map(list)
    }

}