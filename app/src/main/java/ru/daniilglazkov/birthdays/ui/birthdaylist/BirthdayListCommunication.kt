package ru.daniilglazkov.birthdays.ui.birthdaylist

import androidx.annotation.StringRes
import ru.daniilglazkov.birthdays.ui.core.Communication
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString

/**
 * @author Danil Glazkov on 17.06.2022, 14:10
 */
interface BirthdayListCommunication : Communication.Mutable<BirthdayItemUiList> {
    fun showMessage(@StringRes id: Int)

    class Base(
        private val provideString: ProvideString,
    ) : Communication.Post<BirthdayItemUiList>(),
        BirthdayListCommunication
    {
        override fun showMessage(id: Int) {
            val message = BirthdayItemUi.Message(provideString.string(id))
            map(BirthdayItemUiList.Base(listOf(message)))
        }
    }
}