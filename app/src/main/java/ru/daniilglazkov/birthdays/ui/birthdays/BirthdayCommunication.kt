package ru.daniilglazkov.birthdays.ui.birthdays

import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 17.06.2022, 08:01
 */
interface BirthdayCommunication: Communication.Mutable<BirthdayUi> {
    class Base : Communication.Ui<BirthdayUi>(), BirthdayCommunication
}