package ru.daniilglazkov.birthdays.sl.module

import android.content.Context
import ru.daniilglazkov.birthdays.domain.datetime.CurrentTimeInMillis
import ru.daniilglazkov.birthdays.domain.datetime.TriggerTime
import ru.daniilglazkov.birthdays.service.core.receivers.ProvideReceiverWrapper
import ru.daniilglazkov.birthdays.service.core.receivers.ReceiverWrapper
import ru.daniilglazkov.birthdays.service.core.notification.CreateNotificationChannel
import ru.daniilglazkov.birthdays.sl.module.datetime.ProvideZoneOffset

/**
 * @author Danil Glazkov on 19.03.2023, 3:53
 */
interface ServiceModule : ProvideReceiverWrapper {

    fun createNotificationChannels()


    class Base(
        private val context: Context,
        private val provideZoneOffset: ProvideZoneOffset
    ) : ServiceModule {

        override fun createNotificationChannels() {
            CreateNotificationChannel.Reminders(context)
                .createChannel()
        }

        private val receiver by lazy {
            ReceiverWrapper.Notification(context,
                TriggerTime.Morning(provideZoneOffset.zoneOffset()),
                CurrentTimeInMillis.Base
            )
        }

        override fun provideReceiverWrapper() = receiver
    }
}