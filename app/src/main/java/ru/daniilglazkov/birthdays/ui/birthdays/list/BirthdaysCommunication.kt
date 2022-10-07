package ru.daniilglazkov.birthdays.ui.birthdays.list

import androidx.annotation.StringRes
import ru.daniilglazkov.birthdays.core.resources.ProvideString
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayUi
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 17.06.2022, 14:10
 */
interface BirthdaysCommunication : Communication.Mutable<BirthdaysUi> {
    fun map(birthdayUi: BirthdayUi) = map(BirthdaysUi.Base(listOf(birthdayUi)))
    fun showMessage(@StringRes id: Int)

    class Base(
        private val provideString: ProvideString,
    ) : Communication.PostUpdate<BirthdaysUi>(),
        BirthdaysCommunication
    {
        override fun showMessage(id: Int) {
            map(BirthdayUi.Message(provideString.string(id)))
        }
    }
}