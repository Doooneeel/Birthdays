package ru.daniilglazkov.birthdays.ui.core

import androidx.fragment.app.FragmentManager

/**
 * @author Danil Glazkov on 19.06.2022, 12:33
 */
interface ShowScreen {
    fun show(containerId: Int, fragmentManager: FragmentManager)
}