package ru.daniilglazkov.birthdays.ui.core.view.chip

import ru.daniilglazkov.birthdays.ui.birthdaylist.chips.ChipUi

/**
 * @author Danil Glazkov on 16.11.2022, 23:48
 */
fun interface OnChipClickListener {

    fun onChipClick(chipUi: ChipUi)


    object Unit : OnChipClickListener {
        override fun onChipClick(chipUi: ChipUi) = kotlin.Unit
    }
}