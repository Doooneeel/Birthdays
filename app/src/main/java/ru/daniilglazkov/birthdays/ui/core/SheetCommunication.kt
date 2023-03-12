package ru.daniilglazkov.birthdays.ui.core

/**
 * @author Danil Glazkov on 05.02.2023, 7:30
 */
interface SheetCommunication : Communication.Mutable<Unit> {
    class Base : Communication.Ui<Unit>(), SheetCommunication
}