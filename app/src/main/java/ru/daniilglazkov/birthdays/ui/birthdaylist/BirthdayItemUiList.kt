package ru.daniilglazkov.birthdays.ui.birthdaylist

import ru.daniilglazkov.birthdays.ui.core.Mapper
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.BirthdayItemUi

/**
 * @author Danil Glazkov on 10.06.2022, 16:11
 */
interface BirthdayItemUiList {
    fun apply(mapper: Mapper.Unit<List<BirthdayItemUi>>)

    class Base(private val list: List<BirthdayItemUi>) : BirthdayItemUiList {
        override fun apply(mapper: Mapper.Unit<List<BirthdayItemUi>>) = mapper.map(list)
    }
}