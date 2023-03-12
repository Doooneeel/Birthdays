package ru.daniilglazkov.birthdays.ui.birthdaylist

import ru.daniilglazkov.birthdays.ui.core.Mapper

/**
 * @author Danil Glazkov on 10.06.2022, 16:11
 */
interface BirthdayItemUiList {

    fun apply(mapper: Mapper.Unit<List<BirthdayItemUi>>)


    data class Base(private val list: List<BirthdayItemUi>) : BirthdayItemUiList {
        override fun apply(mapper: Mapper.Unit<List<BirthdayItemUi>>) = mapper.map(list)
    }

    data class Message(private val message: String) : BirthdayItemUiList {
        private val messageUi by lazy { listOf(BirthdayItemUi.Message(message)) }

        override fun apply(mapper: Mapper.Unit<List<BirthdayItemUi>>) =
            mapper.map(messageUi)
    }

    object Empty : BirthdayItemUiList {
        override fun apply(mapper: Mapper.Unit<List<BirthdayItemUi>>) =
            mapper.map(emptyList())
    }
}