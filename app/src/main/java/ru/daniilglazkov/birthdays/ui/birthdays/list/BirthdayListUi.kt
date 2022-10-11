package ru.daniilglazkov.birthdays.ui.birthdays.list

import ru.daniilglazkov.birthdays.ui.core.Mapper
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayUi

/**
 * @author Danil Glazkov on 10.06.2022, 16:11
 */
interface BirthdayListUi {
    fun apply(mapper: Mapper.Unit<List<BirthdayUi>>)

    class Base(private val list: List<BirthdayUi>) : BirthdayListUi {
        override fun apply(mapper: Mapper.Unit<List<BirthdayUi>>) = mapper.map(list)
    }

}