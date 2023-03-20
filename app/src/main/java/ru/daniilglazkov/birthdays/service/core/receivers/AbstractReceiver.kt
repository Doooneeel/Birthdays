package ru.daniilglazkov.birthdays.service.core.receivers

import android.content.*
import kotlinx.coroutines.*

/**
 * @author Danil Glazkov on 18.03.2023, 21:25
 */
abstract class AbstractReceiver : BroadcastReceiver() {

    protected open val expectedActions: List<String> = emptyList()

    protected open val handlerCoroutineException = CoroutineExceptionHandler { _, _ -> }

    protected abstract suspend fun handle(context: Context, intent: Intent)


    override fun onReceive(context: Context, intent: Intent) {
        if (expectedActions.isEmpty() || expectedActions.contains(intent.action)) {

            val pendingResult: PendingResult = goAsync()
            val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

            scope.launch(handlerCoroutineException) {
                handle(context, intent)
                pendingResult.finish()
            }
        }
    }
}