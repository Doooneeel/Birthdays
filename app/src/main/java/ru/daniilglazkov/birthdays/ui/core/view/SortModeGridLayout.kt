package ru.daniilglazkov.birthdays.ui.core.view

import android.content.Context
import android.util.AttributeSet
import android.widget.GridLayout
import ru.daniilglazkov.birthdays.domain.showmode.sort.SortMode
import ru.daniilglazkov.birthdays.ui.settings.showmode.SetOnSortModeRadioButtonClick

/**
 * @author Danil Glazkov on 07.10.2022, 18:59
 */
@Suppress("UNCHECKED_CAST")
class SortModeGridLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : GridLayout(
    context,
    attrs
) , AbstractView<SortMode>,
    SetOnSortModeRadioButtonClick
{
    private var radioButtonClick: (SortMode) -> Unit = { }
    private val childViews by lazy {
        List(childCount) { getChildAt(it) as AbstractSortModeRadioButton }
    }

    override fun setOnSortModeRadioButtonClick(block: (SortMode) -> Unit) {
        radioButtonClick = block
    }
    override fun map(source: SortMode) = childViews.forEach { abstractRadioButton ->
        abstractRadioButton.setOnSortModeRadioButtonClick(radioButtonClick)
        abstractRadioButton.map(source)
    }
}