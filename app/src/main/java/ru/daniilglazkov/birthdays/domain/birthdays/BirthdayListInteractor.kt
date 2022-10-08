package ru.daniilglazkov.birthdays.domain.birthdays

import ru.daniilglazkov.birthdays.domain.birthdays.search.*
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.*

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

    class Base(
        private val repository: BirthdayListRepository,
        private val showModeRepository: FetchShowMode,
        private val transformBirthdayList: ShowModeDomain.Mapper<TransformBirthdayList>,
        private val toSearchMapper: BirthdayListDomainToSearchMapper,
    ) : BirthdayListInteractor {
        private val default = BirthdayListDomain.Empty()

        override fun birthdays(
            result: (BirthdayListDomain) -> Unit,
            query: CharSequence,
            notFound: () -> Unit,
            onEmptyCache: () -> Unit
        ) {
            val cachedBirthdays: BirthdayListDomain = repository.read(default)
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
    }
}