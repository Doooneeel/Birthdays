package ru.daniilglazkov.birthdays.ui.birthdays.settings.showmode

import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortMode
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 07.10.2022, 20:33
 */
interface SortModeAbstractView : AbstractView<SortMode>, SetOnSortModeRadioButtonClick

interface SetOnSortModeRadioButtonClick {
    fun setOnSortModeRadioButtonClick(block: (SortMode) -> Unit)
}