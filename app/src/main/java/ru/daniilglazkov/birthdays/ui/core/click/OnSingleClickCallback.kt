package ru.daniilglazkov.birthdays.ui.core.click

import ru.daniilglazkov.birthdays.ui.core.Debounce

/**
 * @author Danil Glazkov on 17.06.2022, 09:35
 */
interface OnSingleClickCallback<T> {
    fun onSingleClick(data: T)
    fun onClick(data: T)

    class Base<T>(
        private val debounce: Debounce,
        private val block: (T) -> Unit
    ) : OnSingleClickCallback<T> {
        override fun onClick(data: T) = block.invoke(data)
        override fun onSingleClick(data: T) = debounce.handle {
            onClick(data)
        }
    }
}