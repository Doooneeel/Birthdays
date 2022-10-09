package ru.daniilglazkov.birthdays.data.showmode.cache

import ru.daniilglazkov.birthdays.data.showmode.ShowModeData
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortMode

/**
 * @author Danil Glazkov on 09.10.2022, 19:36
 */
interface ShowModeDataToCacheMapper : ShowModeData.Mapper<ShowModeCache> {

    class Base : ShowModeDataToCacheMapper {
        override fun map(sort: SortMode, reverse: Boolean, group: Boolean): ShowModeCache =
            ShowModeCache(sort, reverse, group)
    }
}