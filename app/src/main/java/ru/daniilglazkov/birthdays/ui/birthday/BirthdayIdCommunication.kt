package ru.daniilglazkov.birthdays.ui.birthday

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayResponse
import ru.daniilglazkov.birthdays.domain.core.Delete
import ru.daniilglazkov.birthdays.domain.core.Find
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 18.02.2023, 0:30
 */
interface BirthdayIdCommunication : Communication.Mutable<Int> {

    interface Put {
        fun putId(id: Int)
    }

    interface Operation {

        suspend fun find(find: Find.Suspend<BirthdayResponse>): BirthdayResponse

        suspend fun delete(delete: Delete.Suspend)

    }

    interface Combo : Operation, Put


    class Base : Communication.Ui<Int>(-1), BirthdayIdCommunication, Combo {

        override suspend fun find(find: Find.Suspend<BirthdayResponse>) = find.find(id = value)

        override suspend fun delete(delete: Delete.Suspend) = delete.delete(id = value)

        override fun putId(id: Int) = put(id)

    }
}

