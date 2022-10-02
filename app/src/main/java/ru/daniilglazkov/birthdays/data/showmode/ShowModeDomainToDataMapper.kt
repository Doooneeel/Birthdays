package ru.daniilglazkov.birthdays.data.showmode

import ru.daniilglazkov.birthdays.domain.birthdays.showmode.ShowModeDomain

/**
 * @author Danil Glazkov on 01.09.2022, 21:31
 */
interface ShowModeDomainToDataMapper : ShowModeDomain.Mapper<ShowModeData> {
    class Base : ShowModeDomain.Mapper.Abstract<ShowModeData>(ShowModeData::Base),
        ShowModeDomainToDataMapper
}