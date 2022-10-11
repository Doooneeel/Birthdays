package ru.daniilglazkov.birthdays.ui.birthdays.list

import androidx.annotation.StringRes
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayUi
import ru.daniilglazkov.birthdays.ui.core.Communication
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString

/**
 * @author Danil Glazkov on 17.06.2022, 14:10
 */
interface BirthdaysCommunication : Communication.Mutable<BirthdayListUi> {
    fun map(birthdayUi: BirthdayUi) = map(BirthdayListUi.Base(listOf(birthdayUi)))
    fun showMessage(@StringRes id: Int)

    class Base(
        private val provideString: ProvideString,
    ) : Communication.Post<BirthdayListUi>(),
        BirthdaysCommunication
    {
        override fun showMessage(id: Int) {
            map(BirthdayUi.Message(provideString.string(id)))
        }
    }
}