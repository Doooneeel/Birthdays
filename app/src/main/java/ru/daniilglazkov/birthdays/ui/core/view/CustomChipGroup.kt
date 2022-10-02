package ru.daniilglazkov.birthdays.ui.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.View.OnClickListener
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

/**
 * @author Danil Glazkov on 28.07.2022, 02:46
 */
class CustomChipGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ChipGroup(
    context,
    attrs
) , AbstractView.TextList {

    private val chips: List<Chip> = mutableListOf<Chip>().apply {
        repeat(childCount) { add(getChildAt(it) as Chip) }
    }
    private var onChipClickListener = OnClickListener { }

    override fun map(source: List<String>) {
        removeAllViews()
        source.forEach(::addChip)
    }

    fun setOnChipClickListener(onClickListener: OnClickListener) {
        onChipClickListener = onClickListener
        chips.forEach { it.setOnClickListener(onClickListener) }
    }

    private fun addChip(text: String) = addView(Chip(context).also {
        it.setOnClickListener(onChipClickListener)
        it.text = text
    })
}