package ru.daniilglazkov.birthdays.ui.birthdays.showmode

import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortMode
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 04.08.2022, 02:32
 */
interface ShowModeUi {

    fun apply(
        map: Map<AbstractView.Check, SortMode>,
        reverse: AbstractView.Check,
        group: AbstractView.Check
    )

    class Base(
        private val sort: SortMode,
        private val reverse: Boolean,
        private val group: Boolean,
    ) : ShowModeUi {
        override fun apply(
            map: Map<AbstractView.Check, SortMode>,
            reverse: AbstractView.Check,
            group: AbstractView.Check
        ) {
            map.forEach { (checkView: AbstractView.Check, sortMode: SortMode) ->
                checkView.map(sortMode == sort)
            }
            reverse.map(this.reverse)
            group.map(this.group)
        }
    }
}