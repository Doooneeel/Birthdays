package ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.state

import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 04.10.2022, 09:55
 */
interface RecyclerState {
    fun apply(view: AbstractView.Recycler)

    abstract class Abstract(
        private val scrollUp: Boolean,
        private val nestedScrollEnabled: Boolean
    ) : RecyclerState {
        override fun apply(view: AbstractView.Recycler) {
            view.scrollUp(scrollUp)
            view.nestedScroll(nestedScrollEnabled)
        }
    }
    class Base(
        scrollUp: Boolean,
        nestedScrollEnabled: Boolean
    ) : Abstract(
        scrollUp,
        nestedScrollEnabled
    )
    object Disable : Abstract(scrollUp = false, nestedScrollEnabled = false)
}