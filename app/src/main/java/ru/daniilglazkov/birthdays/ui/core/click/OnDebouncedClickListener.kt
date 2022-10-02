package ru.daniilglazkov.birthdays.ui.core.click

import android.view.View
import ru.daniilglazkov.birthdays.ui.core.Debounce

/**
 * @author Danil Glazkov on 25.07.2022, 07:43
 */
interface OnDebouncedClickListener : View.OnClickListener {

    abstract class Abstract(
        private val debounce: Debounce,
        private val listener: View.OnClickListener,
    ) : OnDebouncedClickListener {
        override fun onClick(view: View) = debounce.handle {
            listener.onClick(view)
        }
    }
    class SlightDelay(onClick: View.OnClickListener) : Abstract(
        Debounce.SlightDelay(),
        onClick
    )
    class MediumDelay(onClick: View.OnClickListener) : Abstract(
        Debounce.MediumDelay(),
        onClick
    )
    class LongDelay(onClick: View.OnClickListener) : Abstract(
        Debounce.LongDelay(),
        onClick
    )

    class Base(
        private val debounce: Debounce,
        private val onClickListener: View.OnClickListener
    ) : OnDebouncedClickListener {
        constructor(debounce: Debounce, block: () -> Unit) : this(
            debounce,
            View.OnClickListener { block.invoke() }
        )
        override fun onClick(view: View) = debounce.handle {
            onClickListener.onClick(view)
        }
    }
}


