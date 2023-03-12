package ru.daniilglazkov.birthdays.ui.core.view.sortmode

import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode

/**
 * @author Danil Glazkov on 07.10.2022, 20:34
 */
interface OnSortModeRadioButtonClick {
    fun setOnSortModeRadioButtonClick(selectedMode: (SortMode) -> Unit)
}