package ru.daniilglazkov.birthdays.ui.birthdays.list.chips

import ru.daniilglazkov.birthdays.core.Clear
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 11.08.2022, 04:51
 */
interface BirthdayChipCommunication: Communication.Mutable<BirthdayListChips>, Clear {
    class Base : Communication.UiUpdate<BirthdayListChips>(), BirthdayChipCommunication {
        override fun clear() = map(BirthdayListChips.Empty())
    }
}