package ru.daniilglazkov.birthdays.data.showmode

import ru.daniilglazkov.birthdays.domain.showmode.ShowModeDomain
import ru.daniilglazkov.birthdays.domain.showmode.sort.SortMode

/**
 * @author Danil Glazkov on 09.10.2022, 19:31
 */
interface ShowModeDataToDomainMapper : ShowModeData.Mapper<ShowModeDomain> {

    class Base : ShowModeDataToDomainMapper {
        override fun map(sort: SortMode, reverse: Boolean, group: Boolean): ShowModeDomain =
            ShowModeDomain.Base(sort, reverse, group)
    }
}