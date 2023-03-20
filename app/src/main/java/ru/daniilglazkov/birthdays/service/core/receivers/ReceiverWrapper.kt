package ru.daniilglazkov.birthdays.service.core.receivers

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import ru.daniilglazkov.birthdays.domain.datetime.CurrentTimeInMillis
import ru.daniilglazkov.birthdays.domain.datetime.TriggerTime
import ru.daniilglazkov.birthdays.service.birthday.notification.NotificationReceiver
import ru.daniilglazkov.birthdays.service.intents.CreatePendingIntent

/**
 * @author Danil Glazkov on 17.03.2023, 20:17
 */
interface ReceiverWrapper {

    fun start()


    class Notification(
        private val context: Context,
        private val triggerTime: TriggerTime,
        private val currentTimeMillis: CurrentTimeInMillis,
    ) : ReceiverWrapper {
        override fun start() {
            val alarmManager = context.getSystemService(AlarmManager::class.java)

            val notificationIntent = Intent(context, NotificationReceiver::class.java)

            val pending = CreatePendingIntent.Broadcast(context, notificationIntent)
                .create(ID)

            alarmManager.cancel(pending)
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                triggerTime.time(currentTimeMillis.currentTimeMillis()),
                AlarmManager.INTERVAL_DAY,
                pending
            )
        }

        companion object {
            private const val ID = 0
        }
    }
}