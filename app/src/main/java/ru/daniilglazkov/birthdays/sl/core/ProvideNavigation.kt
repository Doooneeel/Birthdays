package ru.daniilglazkov.birthdays.sl.core

import ru.daniilglazkov.birthdays.ui.core.Navigation

/**
 * @author Danil Glazkov on 09.08.2022, 02:08
 */
interface ProvideNavigation {
    fun navigation(): Navigation.Mutable
}