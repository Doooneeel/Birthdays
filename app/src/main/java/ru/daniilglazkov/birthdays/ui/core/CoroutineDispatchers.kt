package ru.daniilglazkov.birthdays.ui.core

import kotlinx.coroutines.*

/**
 * @author Danil Glazkov on 16.02.2023, 19:11
 */
interface CoroutineDispatchers {

    suspend fun changeToUi(block: suspend CoroutineScope. () -> Unit)

    fun launchUI(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit): Job

    fun launchBackground(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit): Job


    abstract class Abstract(
        private val ui: CoroutineDispatcher,
        private val background: CoroutineDispatcher,
    ) : CoroutineDispatchers {

        override fun launchBackground(
            scope: CoroutineScope,
            block: suspend CoroutineScope.() -> Unit
        ): Job = scope.launch(background, block = block)

        override fun launchUI(
            scope: CoroutineScope,
            block: suspend CoroutineScope.() -> Unit
        ): Job = scope.launch(ui, block = block)

        override suspend fun changeToUi(block: suspend CoroutineScope.() -> Unit) =
            withContext(ui, block)
    }

    class Base : Abstract(Dispatchers.Main, Dispatchers.IO)
}