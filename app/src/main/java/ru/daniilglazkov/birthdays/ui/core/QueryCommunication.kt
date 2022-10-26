package ru.daniilglazkov.birthdays.ui.core

/**
 * @author Danil Glazkov on 06.10.2022, 14:59
 */
interface QueryCommunication : Communication.Mutable<CharSequence> {
    fun executeQuery(query: (CharSequence) -> Unit)

    class Base : Communication.Ui<CharSequence>(initValue = ""), QueryCommunication {
        override fun executeQuery(query: (CharSequence) -> Unit) = query.invoke(value)
    }
}