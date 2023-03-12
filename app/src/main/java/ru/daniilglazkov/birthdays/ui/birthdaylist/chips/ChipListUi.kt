package ru.daniilglazkov.birthdays.ui.birthdaylist.chips

import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 11.08.2022, 05:10
 */
interface ChipListUi {

    fun apply(view: AbstractView.List<ChipUi>)


    abstract class Abstract(private val chips: List<ChipUi>) : ChipListUi {
        override fun apply(view: AbstractView.List<ChipUi>) = view.map(chips)
    }

    data class Base(private val chips: List<ChipUi>) : Abstract(chips) {
        constructor(vararg chips: ChipUi) : this(chips.toList())
    }

    object Empty : Abstract(emptyList())
}
