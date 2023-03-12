package ru.daniilglazkov.birthdays.ui.settings

import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 04.08.2022, 02:32
 */
interface SettingsUi {

    fun apply(reverse: AbstractView.Check, group: AbstractView.Check)

    fun applySortMode(view: AbstractView<SortMode>)


    data class Base(
        private val sort: SortMode,
        private val reverse: Boolean,
        private val group: Boolean,
    ) : SettingsUi {

        override fun applySortMode(view: AbstractView<SortMode>) = view.map(sort)

        override fun apply(reverse: AbstractView.Check, group: AbstractView.Check) {
            reverse.map(this.reverse)
            group.map(this.group)
        }
    }
}