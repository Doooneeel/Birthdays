package ru.daniilglazkov.birthdays.ui.core.view

import android.content.Context
import android.util.AttributeSet
import android.widget.GridLayout
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode
import ru.daniilglazkov.birthdays.ui.core.view.button.AbstractSortModeRadioButton
import ru.daniilglazkov.birthdays.ui.core.view.sortmode.OnSortModeRadioButtonClick

/**
 * @author Danil Glazkov on 07.10.2022, 18:59
 */
class SortModeGridLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : GridLayout(
    context,
    attrs
) , AbstractView<SortMode>,
    OnSortModeRadioButtonClick
{
    private var radioButtonClick: (SortMode) -> Unit = { }

    @Suppress("UNCHECKED_CAST")
    private val childViews by lazy {
        List(childCount) { getChildAt(it) as AbstractSortModeRadioButton }
    }

    override fun setOnSortModeRadioButtonClick(selectedMode: (SortMode) -> Unit) {
        radioButtonClick = selectedMode
    }

    override fun map(source: SortMode) = childViews.forEach { radioButton ->
        radioButton.setOnSortModeRadioButtonClick(radioButtonClick)
        radioButton.map(source)
    }
}