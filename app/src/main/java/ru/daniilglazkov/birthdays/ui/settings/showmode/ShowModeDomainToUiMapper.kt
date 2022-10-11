package ru.daniilglazkov.birthdays.ui.settings.showmode

import ru.daniilglazkov.birthdays.domain.showmode.ShowModeDomain
import ru.daniilglazkov.birthdays.domain.showmode.sort.SortMode

/**
 * @author Danil Glazkov on 01.09.2022, 21:29
 */
interface ShowModeDomainToUiMapper : ShowModeDomain.Mapper<ShowModeUi> {

    class Base : ShowModeDomainToUiMapper {
        override fun map(sort: SortMode, reverse: Boolean, group: Boolean): ShowModeUi =
            ShowModeUi.Base(sort, reverse, group)
    }
}