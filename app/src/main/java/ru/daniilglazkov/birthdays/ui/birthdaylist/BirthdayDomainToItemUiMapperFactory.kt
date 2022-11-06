package ru.daniilglazkov.birthdays.ui.birthdaylist

import ru.daniilglazkov.birthdays.domain.date.EventIsToday
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.core.Factory
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString
import java.time.LocalDate

/**
 * @author Danil Glazkov on 10.10.2022, 19:04
 */
interface BirthdayDomainToItemUiMapperFactory : Factory<BirthdayType, BirthdayDomainToItemUiMapper> {

    class Base(
        resources: ProvideString,
        daysToBirthdayDateDifference: DateDifference,
        eventIsToday: EventIsToday,
        now: LocalDate,
        private val next: BirthdayDomainToItemUiMapperFactory = Error
    ) : BirthdayDomainToItemUiMapperFactory {

        private val headerMapper = BirthdayDomainToItemUiMapper.Header()
        private val baseMapper = BirthdayDomainToItemUiMapper.Base(
            resources, eventIsToday, daysToBirthdayDateDifference, now
        )
        override fun create(source: BirthdayType) = when (source) {
            is BirthdayType.Base -> baseMapper
            is BirthdayType.Header -> headerMapper
            else -> next.create(source)
        }
    }

    object Error : BirthdayDomainToItemUiMapperFactory {
        override fun create(source: BirthdayType) =
            throw IllegalStateException("Unknown type: $source")
    }
}