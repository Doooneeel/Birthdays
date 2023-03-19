package ru.daniilglazkov.birthdays.sl.module.datetime

import java.time.ZoneOffset

/**
 * @author Danil Glazkov on 19.03.2023, 4:33
 */
interface ProvideZoneOffset {
    fun zoneOffset(): ZoneOffset
}