package ru.daniilglazkov.birthdays.ui.birthday

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayResponse
import ru.daniilglazkov.birthdays.domain.core.Delete
import ru.daniilglazkov.birthdays.domain.core.Find
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.zodiac.ZodiacsUi

/**
 * @author Danil Glazkov on 11.03.2023, 15:31
 */
class TestBirthdayCommunications : BirthdayCommunications.Mutable {

    val putDeleteStateCalledList = mutableListOf<Boolean>()
    var handleTrueDeleteStateCallCount = 0
    val putBirthdayCalledList = mutableListOf<BirthdayUi>()
    val putZodiacsCalledList = mutableListOf<ZodiacsUi>()
    val putErrorMessageCalledList = mutableListOf<ErrorMessage>()
    val putIdCalledList = mutableListOf<Int>()
    var deleteState = false
    var deleteCallCount = 0
    var findCallCount = 0
    var id = 0

    override fun putDeleteState(state: Boolean) { putDeleteStateCalledList.add(state) }

    override fun handleTrueDeleteState(block: () -> Unit) {
        ++handleTrueDeleteStateCallCount

        if (deleteState) {
            block.invoke()
        }
    }

    override fun putBirthday(birthday: BirthdayUi) { putBirthdayCalledList.add(birthday) }

    override fun putZodiacs(zodiacs: ZodiacsUi) { putZodiacsCalledList.add(zodiacs) }

    override fun putError(message: ErrorMessage) {
        putErrorMessageCalledList.add(message)
    }

    override suspend fun find(find: Find.Suspend<BirthdayResponse>): BirthdayResponse {
        ++findCallCount
        return find.find(id)
    }

    override suspend fun delete(delete: Delete.Suspend) {
        ++deleteCallCount
        delete.delete(id)
    }

    override fun putId(id: Int) { putIdCalledList.add(id) }

    override fun observeBirthday(owner: LifecycleOwner, observer: Observer<BirthdayUi>) = Unit

    override fun observeZodiacs(owner: LifecycleOwner, observer: Observer<ZodiacsUi>) = Unit

    override fun observeDeleteState(owner: LifecycleOwner, observer: Observer<Boolean>)= Unit

    override fun observeError(owner: LifecycleOwner, observer: Observer<ErrorMessage>) = Unit
}