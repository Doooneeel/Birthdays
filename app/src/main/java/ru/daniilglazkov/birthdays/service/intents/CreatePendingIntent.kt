package ru.daniilglazkov.birthdays.service.intents

import android.app.PendingIntent
import android.content.Context
import android.content.Intent

/**
 * @author Danil Glazkov on 17.03.2023, 1:20
 */
interface CreatePendingIntent {

    fun create(id: Int): PendingIntent


    class MainActivity(private val context: Context) : CreatePendingIntent {
        override fun create(id: Int): PendingIntent {
            val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

            return PendingIntent.getActivity(context, id, MainActivityIntent(context), flags)
        }
    }

    class Broadcast(
        private val context: Context,
        private val intent: Intent,
    ) : CreatePendingIntent {
        override fun create(id: Int): PendingIntent {
            val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

            return PendingIntent.getBroadcast(context, id, intent, flags)
        }
    }
}