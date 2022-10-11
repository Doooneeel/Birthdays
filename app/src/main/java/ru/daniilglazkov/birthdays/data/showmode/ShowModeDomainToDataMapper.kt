package ru.daniilglazkov.birthdays.data.showmode

import ru.daniilglazkov.birthdays.domain.showmode.ShowModeDomain
import ru.daniilglazkov.birthdays.domain.showmode.sort.SortMode

/**
 * @author Danil Glazkov on 01.09.2022, 21:31
 */
interface ShowModeDomainToDataMapper : ShowModeDomain.Mapper<ShowModeData> {

    class Base : ShowModeDomainToDataMapper {
        override fun map(sort: SortMode, reverse: Boolean, group: Boolean): ShowModeData =
            ShowModeData.Base(sort, reverse, group)
    }
}
