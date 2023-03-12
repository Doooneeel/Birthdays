package ru.daniilglazkov.birthdays.ui.core.navigation

import androidx.fragment.app.FragmentManager

/**
 * @author Danil Glazkov on 19.06.2022, 12:33
 */
interface ShowFragment {
    fun show(containerId: Int, fragmentManager: FragmentManager)
}