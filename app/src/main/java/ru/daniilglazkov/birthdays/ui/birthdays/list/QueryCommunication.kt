package ru.daniilglazkov.birthdays.ui.birthdays.list

import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 06.10.2022, 14:59
 */
interface QueryCommunication : Communication.Mutable<CharSequence> {
    fun executeQuery(query: (CharSequence) -> Unit)

    class Base : Communication.UiUpdate<CharSequence>(""), QueryCommunication {
        override fun executeQuery(query: (CharSequence) -> Unit) = query.invoke(value)
    }
}