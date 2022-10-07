package ru.daniilglazkov.birthdays.ui.birthdays.settings.showmode

import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortMode
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 04.08.2022, 02:32
 */
interface ShowModeUi {

    fun applySortMode(views: AbstractView<SortMode>)
    fun applyAdditionalSettings(reverse: AbstractView.Check, group: AbstractView.Check)

    class Base(
        private val sort: SortMode,
        private val reverse: Boolean,
        private val group: Boolean,
    ) : ShowModeUi {
        override fun applySortMode(views: AbstractView<SortMode>) = views.map(sort)

        override fun applyAdditionalSettings(reverse: AbstractView.Check, group: AbstractView.Check) {
            reverse.map(this.reverse)
            group.map(this.group)
        }
    }
}