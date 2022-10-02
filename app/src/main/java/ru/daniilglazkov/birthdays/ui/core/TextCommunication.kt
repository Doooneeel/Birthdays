package ru.daniilglazkov.birthdays.ui.core

/**
 * @author Danil Glazkov on 12.08.2022, 23:55
 */
interface TextCommunication : Communication.Mutable<String> {
    class Base : Communication.UiUpdate<String>(), TextCommunication
}