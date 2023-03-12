package ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.state

import ru.daniilglazkov.birthdays.ui.core.view.AbstractView
import ru.daniilglazkov.birthdays.ui.core.view.recycler.ScrollMode

/**
 * @author Danil Glazkov on 04.10.2022, 09:55
 */
interface RecyclerState {

    fun apply(view: AbstractView.Recycler)


    abstract class Abstract(
        private val mode: ScrollMode,
        private val nestedScroll: Boolean
    ) : RecyclerState {
        override fun apply(view: AbstractView.Recycler) {
            view.scroll(mode)
            view.nestedScroll(nestedScroll)
        }
    }

    data class Base(
        private val mode: ScrollMode,
        private val nestedScroll: Boolean = true
    ) : Abstract(mode, nestedScroll)

    object Disable : Abstract(ScrollMode.NoScroll, nestedScroll = false)
}