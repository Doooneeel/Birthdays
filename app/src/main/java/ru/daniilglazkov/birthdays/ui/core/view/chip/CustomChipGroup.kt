package ru.daniilglazkov.birthdays.ui.core.view.chip

import android.content.Context
import android.util.AttributeSet
import android.view.View.OnClickListener
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 28.07.2022, 02:46
 */
class CustomChipGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ChipGroup(
    context,
    attrs
) , AbstractView.List<String>,
    SetOnChipClickListener
{
    private val chips = buildList {
        repeat(childCount) { add(getChildAt(it) as Chip) }
    }
    private var onChipClickListener = OnClickListener { }

    override fun setOnChipClickListener(onClickListener: OnClickListener) {
        chips.forEach { chip -> chip.setOnClickListener(onClickListener) }
        onChipClickListener = onClickListener
    }
    override fun map(source: List<String>) {
        removeAllViews()

        source.forEach { title: String ->
            addView(CustomChip(context).also { newChip ->
                newChip.setOnClickListener(onChipClickListener)
                newChip.map(title)
            })
        }
    }
}