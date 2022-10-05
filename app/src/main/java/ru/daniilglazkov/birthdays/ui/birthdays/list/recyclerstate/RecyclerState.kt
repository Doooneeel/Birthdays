package ru.daniilglazkov.birthdays.ui.birthdays.list.recyclerstate

import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 04.10.2022, 09:55
 */
interface RecyclerState {
    fun apply(recyclerView: AbstractView.Recycler)

    abstract class Abstract(
        private val scrollUp: Boolean,
        private val nestedScrollEnabled: Boolean
    ) : RecyclerState {
        override fun apply(recyclerView: AbstractView.Recycler) {
            recyclerView.scrollUp(scrollUp)
            recyclerView.nestedScroll(nestedScrollEnabled)
        }
    }
    class Base(
        scrollUp: Boolean,
        nestedScrollEnabled: Boolean
    ) : Abstract(
        scrollUp,
        nestedScrollEnabled
    )
    class Disable : Abstract(scrollUp = false, nestedScrollEnabled = false)
}