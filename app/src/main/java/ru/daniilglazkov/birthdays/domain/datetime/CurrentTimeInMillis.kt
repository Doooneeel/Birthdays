package ru.daniilglazkov.birthdays.domain.datetime

import java.util.Calendar

/**
 * @author Danil Glazkov on 15.03.2023, 23:22
 */
interface CurrentTimeInMillis {

    fun currentTimeMillis(): Long


    object Base : CurrentTimeInMillis {
        override fun currentTimeMillis() = Calendar.getInstance().timeInMillis
    }
}