package ru.daniilglazkov.birthdays.ui.birthdays.list.scrollup

import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 19.09.2022, 16:32
 */
interface ScrollUp {
    fun apply(view: AbstractView.Scroll)

    class Base(private val needScrollToScrollUp: Boolean) : ScrollUp {
        override fun apply(view: AbstractView.Scroll) = view.map(needScrollToScrollUp)
    }
}