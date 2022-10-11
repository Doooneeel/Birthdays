package ru.daniilglazkov.birthdays.ui.core.view

import ru.daniilglazkov.birthdays.ui.core.Mapper
import java.time.LocalDate

/**
 * @author Danil Glazkov on 27.08.2022, 14:25
 */
interface AbstractView<T> : Mapper.Unit<T> {

    interface Text : AbstractView<String> {

        object Empty : Text {
            override fun map(source: String) = Unit
        }
    }
    interface Check : AbstractView<Boolean>

    interface Date : AbstractView<LocalDate>

    interface List<T> : AbstractView<kotlin.collections.List<T>>


    interface Recycler {
        fun nestedScroll(enabled: Boolean)
        fun scrollUp(needToScroll: Boolean)
    }

}