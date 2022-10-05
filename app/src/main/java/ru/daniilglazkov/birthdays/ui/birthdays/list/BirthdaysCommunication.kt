package ru.daniilglazkov.birthdays.ui.birthdays.list

import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayUi
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 17.06.2022, 14:10
 */
interface BirthdaysCommunication : Communication.Mutable<BirthdaysUi> {
    fun map(birthdayUi: BirthdayUi) = map(BirthdaysUi.Base(listOf(birthdayUi)))

    class Base : Communication.PostUpdate<BirthdaysUi>(), BirthdaysCommunication {
        override fun map(source: BirthdaysUi) = liveData.postValue(source)
    }
}