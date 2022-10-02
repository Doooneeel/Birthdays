package ru.daniilglazkov.birthdays.ui.birthdays.list

import ru.daniilglazkov.birthdays.ui.birthdays.showmode.BirthdaysChips
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 11.08.2022, 04:51
 */
interface BirthdayChipCommunication: Communication.Mutable<BirthdaysChips> {
    class Base : Communication.UiUpdate<BirthdaysChips>(), BirthdayChipCommunication
}