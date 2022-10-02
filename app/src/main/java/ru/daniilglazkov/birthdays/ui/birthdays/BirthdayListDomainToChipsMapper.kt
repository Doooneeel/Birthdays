package ru.daniilglazkov.birthdays.ui.birthdays

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.core.resources.ProvideString
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayType
import ru.daniilglazkov.birthdays.ui.birthdays.showmode.BirthdaysChips
import java.time.LocalDate

/**
 * @author Danil Glazkov on 01.09.2022, 21:25
 */
interface BirthdayListDomainToChipsMapper : BirthdayListDomain.Mapper<BirthdaysChips> {

    class OnlyName(
        private val isHeader: BirthdayDomain.CheckMapper,
    ) : BirthdayListDomainToChipsMapper {
        override fun map(list: List<BirthdayDomain>): BirthdaysChips {
            val headers = list.filter { it.map(isHeader) }
            return BirthdaysChips.Base(headers.map { it.map(header) })
        }
        private val header = object : BirthdayDomain.Mapper<String> {
            override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): String =
                name
        }
    }
    class NameAndQuantityWithTotal(
        private val isHeader: BirthdayDomain.CheckMapper,
        private val chipTextFormat: BirthdayChipTextFormat,
        private val totalChipTextFormat: BirthdayChipTextFormat,
        provideString: ProvideString
    ) : BirthdayListDomainToChipsMapper {
        private val total: String = provideString.string(R.string.total)

        override fun map(list: List<BirthdayDomain>): BirthdaysChips {
            if (list.isEmpty()) {
                return BirthdaysChips.Empty()
            }
            val headerIndexes = mutableListOf<Int>().also { headerIndex ->
                list.forEachIndexed { index, birthdayDomain ->
                    if (birthdayDomain.map(isHeader)) headerIndex.add(index)
                }
            }
            val result: MutableList<String> = mutableListOf(
                totalChipTextFormat.format(total, list.count { it.map(isHeader).not() })
            )
            headerIndexes.forEachIndexed { index, headerIndex ->
                result.add(list[headerIndex].map(HeaderWithCount(
                    if (index + 1 > headerIndexes.lastIndex) {
                        list.lastIndex - headerIndexes[index]
                    } else {
                        headerIndexes[index + 1] - headerIndexes[index] - 1
                    }
                )))
            }
            return BirthdaysChips.Base(result)
        }

        private inner class HeaderWithCount(private val count: Int) : BirthdayDomain.Mapper<String> {
            override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): String =
                chipTextFormat.format(name, count)
        }
    }

}