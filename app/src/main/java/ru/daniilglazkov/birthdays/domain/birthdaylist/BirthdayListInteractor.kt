package ru.daniilglazkov.birthdays.domain.birthdaylist

import ru.daniilglazkov.birthdays.domain.birthdaylist.search.BirthdayListDomainToSearchMapper
import ru.daniilglazkov.birthdays.domain.birthdaylist.search.BirthdayListSearchWrapper
import ru.daniilglazkov.birthdays.domain.showmode.*

/**
 * @author Danil Glazkov on 10.06.2022, 00:53
 */
interface BirthdayListInteractor {

    fun birthdays(
        result: (BirthdayListDomain) -> Unit,
        query: CharSequence = "",
        notFound: () -> Unit = { },
        onEmptyCache: () -> Unit
    )
    fun reload()

    class Base(
        private val repository: BirthdayListRepository,
        private val showModeRepository: FetchShowMode,
        private val transformBirthdayList: ShowModeDomain.Mapper<TransformBirthdayList>,
        private val toSearchMapper: BirthdayListDomainToSearchMapper,
    ) : BirthdayListInteractor {
        private var cachedBirthdays: BirthdayListDomain = repository.birthdays()

        override fun birthdays(
            result: (BirthdayListDomain) -> Unit,
            query: CharSequence,
            notFound: () -> Unit,
            onEmptyCache: () -> Unit
        ) {
            if (cachedBirthdays.isEmpty()) {
                onEmptyCache.invoke()
                return
            }
            val showMode: ShowModeDomain = showModeRepository.fetchShowMode()
            val transformBirthdays: TransformBirthdayList = showMode.map(transformBirthdayList)

            if (query.isNotEmpty()) {
                val searchWrapper: BirthdayListSearchWrapper = cachedBirthdays.map(toSearchMapper)
                val found: BirthdayListDomain = searchWrapper.search(query)
                    
                if (found.isEmpty()) {
                    notFound.invoke()
                } else {
                    result.invoke(transformBirthdays.transform(found))
                }
            } else {
                result.invoke(transformBirthdays.transform(cachedBirthdays))
            }
        }
        override fun reload() {
            cachedBirthdays = repository.birthdays()
        }
    }
}
