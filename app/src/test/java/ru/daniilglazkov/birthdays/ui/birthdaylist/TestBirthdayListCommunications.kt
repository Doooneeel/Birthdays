package ru.daniilglazkov.birthdays.ui.birthdaylist

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListResponse
import ru.daniilglazkov.birthdays.domain.birthdaylist.FetchBirthdays
import ru.daniilglazkov.birthdays.ui.birthdaylist.chips.ChipListUi
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.state.RecyclerState

/**
 * @author Danil Glazkov on 11.03.2023, 15:06
 */
class TestBirthdayListCommunications : BirthdayListCommunications.Mutable {

    lateinit var query: CharSequence

    var fetchBirthdaysCallCount = 0
    val putChipsCalledList = mutableListOf<ChipListUi>()
    val putBirthdaysCalledList = mutableListOf<BirthdayItemUiList>()
    val putQueryCalledList = mutableListOf<CharSequence>()
    val putRecyclerStateCalledList = mutableListOf<RecyclerState>()
    val smoothScrollCalledList = mutableListOf<Int>()


    override fun putChips(chips: ChipListUi) { putChipsCalledList.add(chips) }

    override suspend fun fetchBirthdays(fetchBirthdays: FetchBirthdays): BirthdayListResponse {
        ++fetchBirthdaysCallCount
        return fetchBirthdays.birthdays(query)
    }

    override fun putBirthdays(birthdays: BirthdayItemUiList) { putBirthdaysCalledList.add(birthdays) }

    override fun putQuery(query: CharSequence) { putQueryCalledList.add(query) }

    override fun putRecyclerState(state: RecyclerState) { putRecyclerStateCalledList.add(state) }

    override fun smoothScroll(position: Int) { smoothScrollCalledList.add(position) }

    override fun observeRecyclerState(
        owner: LifecycleOwner,
        observer: Observer<RecyclerState>
    ) = Unit

    override fun observeChips(owner: LifecycleOwner, observer: Observer<ChipListUi>) = Unit

    override fun observeBirthdayList(
        owner: LifecycleOwner,
        observer: Observer<BirthdayItemUiList>
    ) = Unit
}