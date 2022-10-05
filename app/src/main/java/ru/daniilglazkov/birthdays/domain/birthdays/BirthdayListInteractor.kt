package ru.daniilglazkov.birthdays.domain.birthdays

import ru.daniilglazkov.birthdays.core.Read
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.ShowModeDomain
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.TransformBirthdayList

/**
 * @author Danil Glazkov on 10.06.2022, 00:53
 */
interface BirthdayListInteractor {

    fun birthdays(
        result: (BirthdayListDomain) -> Unit,
        filter: CharSequence = "",
        notFound: () -> Unit = { },
        onEmptyCache: () -> Unit
    )

    class Base(
        private val repository: BirthdayListRepository,
        private val showModeRepository: Read<ShowModeDomain>,
        private val transformBirthdayList: ShowModeDomain.Mapper<TransformBirthdayList>,
    ) : BirthdayListInteractor {
        private val default = BirthdayListDomain.Empty()

        override fun birthdays(
            result: (BirthdayListDomain) -> Unit,
            filter: CharSequence,
            notFound: () -> Unit,
            onEmptyCache: () -> Unit
        ) {
            val cachedBirthdays: BirthdayListDomain = repository.read(default)
            if (cachedBirthdays.isEmpty()) {
                onEmptyCache.invoke()
                return
            }
            val showMode: ShowModeDomain = showModeRepository.read()
            val transformBirthdays: TransformBirthdayList = showMode.map(transformBirthdayList)
            val birthdays: BirthdayListDomain = transformBirthdays.transform(cachedBirthdays)

            result.invoke(birthdays)
            //TODO make search
        }
    }
}