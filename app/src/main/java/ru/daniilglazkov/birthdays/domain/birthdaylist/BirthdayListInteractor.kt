package ru.daniilglazkov.birthdays.domain.birthdaylist

import ru.daniilglazkov.birthdays.domain.birthdaylist.search.BirthdaySearch
import ru.daniilglazkov.birthdays.domain.core.DeterminePosition
import ru.daniilglazkov.birthdays.domain.core.FirstLaunch

/**
 * @author Danil Glazkov on 10.06.2022, 00:53
 */
interface BirthdayListInteractor : DeterminePosition, FetchBirthdays, FirstLaunch {

    class Base(
        private val repository: BirthdayListRepository,
        private val modifyBirthdayList: ModifyBirthdayList,
        private val birthdaySearch: BirthdaySearch,
        private val handleRequest: HandleBirthdayListDataRequest
    ) : BirthdayListInteractor {
        private var modifiedList: BirthdayListDomain = BirthdayListDomain.Base()

        override suspend fun birthdays(query: CharSequence) = handleRequest.handle {
            var birthdayList: BirthdayListDomain = repository.birthdays()

            if (query.isNotEmpty()) {
                birthdayList = birthdaySearch.search(birthdayList, query)
            }
            modifiedList = modifyBirthdayList.modify(birthdayList)

            BirthdayListResponse.Success(modifiedList)
        }

        override fun position(id: Int): Int = modifiedList.position(id)

        override fun firstLaunch(): Boolean = repository.firstLaunch()
    }
}