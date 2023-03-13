package ru.daniilglazkov.birthdays.ui

import android.content.SharedPreferences
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import ru.daniilglazkov.birthdays.ui.core.*
import ru.daniilglazkov.birthdays.ui.core.CoroutineDispatchers
import ru.daniilglazkov.birthdays.ui.core.navigation.Navigation
import ru.daniilglazkov.birthdays.ui.core.navigation.NavigationScreen
import ru.daniilglazkov.birthdays.ui.core.resources.ManageResources

/**
 * @author Danil Glazkov on 05.02.2023, 8:12
 */

abstract class BaseUiTest {

    protected class TestSheetCommunication : TestAbstractCommunication<Unit>(), SheetCommunication

    protected class TestNavigation : TestAbstractCommunication<NavigationScreen>(), Navigation.Mutable

    protected class TestManageResources : ManageResources {

        var string: String = ""
        var number: Int = 0
        var stringCount = 0
        var quantityStringCalledList = mutableListOf<Int>()

        override fun string(id: Int): String {
            ++stringCount
            return string
        }

        override fun quantityString(id: Int, value: Int): String {
            quantityStringCalledList.add(value)
            return value.toString()
        }

        override fun number(id: Int): Int = number

        override fun preferences(fileName: String): SharedPreferences {
            throw IllegalArgumentException("not used here")
        }
    }

    protected open class TestAbstractCommunication<T> : Communication.Mutable<T> {
        val mapCalledList = mutableListOf<T>()

        override fun put(value: T) { mapCalledList.add(value) }

        override fun observe(owner: LifecycleOwner, observer: Observer<T>) = Unit
    }

    protected class TestHandleError : HandleError {

        var result: String = ""
        val calledList = mutableListOf<Exception>()

        override fun handle(exception: Exception): String {
            calledList.add(exception)
            return result
        }
    }

    protected open class TestHandleDomainRequest<T> : HandleDomainRequest<T> {

        var handleCallCount = 0
        var result: T? = null

        override fun handle(scope: CoroutineScope, block: suspend () -> T) {
            ++handleCallCount

            runBlocking {
                result = block.invoke()
            }
        }
    }

    @ExperimentalCoroutinesApi
    protected class TestDispatchers(dispatcher: TestDispatcher = UnconfinedTestDispatcher()) :
        CoroutineDispatchers.Abstract(dispatcher, dispatcher)
    {
        var launchUiCallCount = 0
        var launchBackgroundCallCount = 0
        var changeUiCallCount = 0

        val calledList = mutableListOf<Int>()

        override suspend fun changeToUi(block: suspend CoroutineScope.() -> Unit) {
            calledList.add(CHANGE_TO_UI)
            ++changeUiCallCount
            super.changeToUi(block)
        }

        override fun launchBackground(
            scope: CoroutineScope,
            block: suspend CoroutineScope.() -> Unit
        ) : Job {
            calledList.add(BACKGROUND)
            ++launchBackgroundCallCount

            return super.launchBackground(scope, block)
        }

        override fun launchUI(
            scope: CoroutineScope,
            block: suspend CoroutineScope.() -> Unit
        )  : Job {
            calledList.add(UI)
            ++launchUiCallCount

            return super.launchUI(scope, block)
        }

        companion object {
            const val BACKGROUND = 100
            const val UI = 200
            const val CHANGE_TO_UI = 300
        }

    }
}