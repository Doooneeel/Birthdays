package ru.daniilglazkov.birthdays.ui.core.view

import ru.daniilglazkov.birthdays.ui.core.Mapper
import ru.daniilglazkov.birthdays.ui.core.view.recycler.ScrollMode
import java.time.LocalDate

/**
 * @author Danil Glazkov on 27.08.2022, 14:25
 */
interface AbstractView<T> : Mapper.Unit<T> {
    
    interface Text : AbstractView<String> {

        class Test : AbstractTest<String>(), Text
        
        object Empty : Text {
            override fun map(source: String) = Unit
        }
    }

    interface Check : AbstractView<Boolean> {
        class Test : AbstractTest<Boolean>(), Check
    }

    interface Date : AbstractView<LocalDate> {
        class Test : AbstractTest<LocalDate>(), Date
    }

    interface List<T> : AbstractView<kotlin.collections.List<T>>

    interface Recycler {

        fun scroll(scrollMode: ScrollMode)

        fun nestedScroll(enabled: Boolean)

    }

    class Unit<T> : AbstractView<T> {
        override fun map(source: T) = Unit
    }

    open class AbstractTest<T> : AbstractView<T> {
        val calledList = mutableListOf<T>()

        override fun map(source: T) { calledList.add(source) }
    }
}