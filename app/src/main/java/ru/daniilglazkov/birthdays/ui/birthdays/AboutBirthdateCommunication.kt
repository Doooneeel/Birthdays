package ru.daniilglazkov.birthdays.ui.birthdays

import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 29.09.2022, 23:08
 */
interface AboutBirthdateCommunication : Communication.Mutable<AboutBirthdate> {
    class Base : Communication.UiUpdate<AboutBirthdate>(), AboutBirthdateCommunication
}