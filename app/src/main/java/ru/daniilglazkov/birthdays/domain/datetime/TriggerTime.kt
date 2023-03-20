package ru.daniilglazkov.birthdays.domain.datetime

import java.time.*
import java.time.temporal.ChronoUnit

/**
 * @author Danil Glazkov on 18.03.2023, 19:40
 */
interface TriggerTime {

    fun time(nowInMillis: Long): Long


    abstract class Abstract(
        private val zoneOffset: ZoneOffset,
        private val time: LocalTime,
    ) : TriggerTime {
        override fun time(nowInMillis: Long): Long {
            val instant = Instant.ofEpochMilli(nowInMillis)

            var nowAsLocalDateTime = LocalDateTime.ofInstant(instant, zoneOffset)
                .truncatedTo(ChronoUnit.MINUTES)

            if (nowAsLocalDateTime.toLocalTime().isAfter(time)) {
                nowAsLocalDateTime = nowAsLocalDateTime.plusDays(1)
            }

            return nowAsLocalDateTime.withHour(time.hour)
                .withMinute(time.minute)
                .withSecond(time.second)
                .withNano(time.nano)
                .atZone(ZoneId.of(zoneOffset.id))
                .withZoneSameInstant(zoneOffset)
                .toLocalDateTime()
                .atZone(zoneOffset)
                .toInstant()
                .toEpochMilli()
        }
    }

    class Morning(zoneOffset: ZoneOffset) : Abstract(zoneOffset, LocalTime.of(9, 0))
}