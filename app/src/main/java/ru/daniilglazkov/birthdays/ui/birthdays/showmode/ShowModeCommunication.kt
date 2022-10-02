package ru.daniilglazkov.birthdays.ui.birthdays.showmode

import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 02.08.2022, 02:31
 */
interface ShowModeCommunication: Communication.Mutable<ShowModeUi> {
    class Base : Communication.PostUpdate<ShowModeUi>(), ShowModeCommunication
}