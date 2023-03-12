package ru.daniilglazkov.birthdays.ui.core.view.click

import ru.daniilglazkov.birthdays.ui.core.Debounce

/**
 * @author Danil Glazkov on 17.06.2022, 09:35
 */
interface OnClickCallback<T> {

    fun onClick(data: T)


    class Base<T>(private val onClick: (T) -> kotlin.Unit) : OnClickCallback<T> {
        override fun onClick(data: T) = onClick.invoke(data)
    }

    class Debounced<T>(
        private val debounce: Debounce,
        private val onClick: (T) -> kotlin.Unit
    ) : OnClickCallback<T> {
        override fun onClick(data: T) = debounce.handle {
            onClick.invoke(data)
        }
    }

    class Unit<T> : OnClickCallback<T> {
        override fun onClick(data: T) = Unit
    }
}