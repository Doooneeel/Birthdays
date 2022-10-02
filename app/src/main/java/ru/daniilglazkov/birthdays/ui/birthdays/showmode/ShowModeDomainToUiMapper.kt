package ru.daniilglazkov.birthdays.ui.birthdays.showmode

import ru.daniilglazkov.birthdays.domain.birthdays.showmode.ShowModeDomain

/**
 * @author Danil Glazkov on 01.09.2022, 21:29
 */
interface ShowModeDomainToUiMapper : ShowModeDomain.Mapper<ShowModeUi> {
    class Base : ShowModeDomain.Mapper.Abstract<ShowModeUi>(ShowModeUi::Base),
        ShowModeDomainToUiMapper
}