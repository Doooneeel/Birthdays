package ru.daniilglazkov.birthdays.ui.birthdaylist.chips

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayCheckMapper
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString

/**
 * @author Danil Glazkov on 01.09.2022, 21:25
 */
interface BirthdayListDomainToChipsMapper : BirthdayListDomain.Mapper<BirthdayListChips> {

    abstract class Abstract(
        private val birthdayDomainToChipMapper: BirthdayDomainToChipMapper,
        private val chipFilterPredicate: BirthdayCheckMapper,
    ) : BirthdayListDomainToChipsMapper {
        protected abstract fun firstChip(totalCount: Int): String
        protected abstract fun handleEmptyList(): BirthdayListChips

        override fun map(list: List<BirthdayDomain>): BirthdayListChips {
            val headers = list.filter { it.map(chipFilterPredicate) }

            return if (headers.isEmpty()) handleEmptyList() else BirthdayListChips.Base(buildList {
                add(firstChip(totalCount = list.size - headers.size))
                addAll(headers.map { header ->
                    header.map(birthdayDomainToChipMapper)
                })
            })
        }
    }

    class Base(
        birthdayDomainToChipMapper: BirthdayDomainToChipMapper,
        chipPredicate: BirthdayCheckMapper,
        private val provideString: ProvideString,
        private val firstChipTextFormat: ChipTextFormat
    ) : Abstract(
        birthdayDomainToChipMapper,
        chipPredicate
    ) {
        override fun handleEmptyList() = BirthdayListChips.Empty

        override fun firstChip(totalCount: Int) = firstChipTextFormat.format(
            provideString.string(R.string.total),
            totalCount
        )
    }
}