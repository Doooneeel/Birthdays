package ru.daniilglazkov.birthdays.ui.core.view.chip

import ru.daniilglazkov.birthdays.ui.core.view.click.OnClickCallback

/**
 * @author Danil Glazkov on 05.10.2022, 13:12
 */
fun interface SetOnChipClickListener<T> {
    fun setOnChipClickListener(callback: OnClickCallback<T>)
}