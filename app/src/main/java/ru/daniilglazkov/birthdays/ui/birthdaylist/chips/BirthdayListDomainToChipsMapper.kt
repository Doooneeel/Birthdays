package ru.daniilglazkov.birthdays.ui.birthdaylist.chips

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayCheckMapper
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString
import ru.daniilglazkov.birthdays.domain.core.text.AddDelimiter

/**
 * @author Danil Glazkov on 01.09.2022, 21:25
 */
interface BirthdayListDomainToChipsMapper : BirthdayListDomain.Mapper<ChipListUi> {

    class Base(
        private val firstChipDelimiter: AddDelimiter,
        private val chipFilterPredicate: BirthdayCheckMapper,
        private val domainToChipMapper: BirthdayDomainToChipMapper,
        manageResources: ProvideString,
    ) : BirthdayListDomainToChipsMapper {
        private val totalTitle: String by lazy { manageResources.string(R.string.total) }

        override fun map(list: List<BirthdayDomain>): ChipListUi {
            if (list.isEmpty()) {
                return ChipListUi.Empty
            }

            val filteredList: List<BirthdayDomain> = list.filter { it.map(chipFilterPredicate) }

            val totalCount: Int = (list.size - filteredList.size)

            val firstChip: ChipUi = ChipUi.WithoutId(
                firstChipDelimiter.add(totalTitle, totalCount.toString())
            )

            return if (totalCount == list.size) {
                ChipListUi.Base(firstChip)
            } else {
                val chipsUi: List<ChipUi> = filteredList.map { it.map(domainToChipMapper) }

                ChipListUi.Base(buildList {
                    add(firstChip)
                    addAll(chipsUi)
                })
            }
        }
    }
}