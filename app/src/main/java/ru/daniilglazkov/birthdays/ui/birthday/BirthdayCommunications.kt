package ru.daniilglazkov.birthdays.ui.birthday

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayResponse
import ru.daniilglazkov.birthdays.domain.core.Delete
import ru.daniilglazkov.birthdays.domain.core.Find
import ru.daniilglazkov.birthdays.ui.core.*
import ru.daniilglazkov.birthdays.ui.zodiac.ZodiacsCommunication
import ru.daniilglazkov.birthdays.ui.zodiac.ZodiacsUi

/**
 * @author Danil Glazkov on 24.02.2023, 1:25
 */
interface BirthdayCommunications {

    interface Update : BirthdayCommunication.Put, ZodiacsCommunication.Put, ErrorCommunication.Put,
        BirthdayIdCommunication.Combo, DeleteStateCommunication.Put {

        fun handleTrueDeleteState(block: () -> Unit)

    }

    interface Observe : BirthdayCommunication.Observe,
        ZodiacsCommunication.Observe,
        DeleteStateCommunication.Observe,
        ErrorCommunication.Observe


    interface Mutable : Update, Observe


    class Base(
        private val birthdayCommunication: BirthdayCommunication,
        private val idCommunication: BirthdayIdCommunication.Combo,
        private val zodiacsUiCommunication: ZodiacsCommunication,
        private val deleteStateCommunication: DeleteStateCommunication,
        private val errorCommunication: ErrorCommunication,
    ) : Mutable {

        override fun putId(id: Int) = idCommunication.putId(id)

        override fun putError(message: ErrorMessage) = errorCommunication.put(message)

        override fun putZodiacs(zodiacs: ZodiacsUi) = zodiacsUiCommunication.put(zodiacs)

        override fun putBirthday(birthday: BirthdayUi) = birthdayCommunication.put(birthday)

        override fun putDeleteState(state: Boolean) = deleteStateCommunication.put(state)

        override suspend fun delete(delete: Delete.Suspend) = idCommunication.delete(delete)

        override suspend fun find(find: Find.Suspend<BirthdayResponse>) =
            idCommunication.find(find)

        override fun handleTrueDeleteState(block: () -> Unit) =
            deleteStateCommunication.handleTrue(block)

        override fun observeZodiacs(owner: LifecycleOwner, observer: Observer<ZodiacsUi>) =
            zodiacsUiCommunication.observe(owner, observer)

        override fun observeDeleteState(owner: LifecycleOwner, observer: Observer<Boolean>) =
            deleteStateCommunication.observe(owner, observer)

        override fun observeError(owner: LifecycleOwner, observer: Observer<ErrorMessage>) =
            errorCommunication.observe(owner, observer)

        override fun observeBirthday(owner: LifecycleOwner, observer: Observer<BirthdayUi>) =
            birthdayCommunication.observe(owner, observer)
    }
}