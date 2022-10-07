package ru.daniilglazkov.birthdays.ui.core.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

/**
 * @author Danil Glazkov on 03.10.2022, 14:50
 */
abstract class AbstractButtonCustomView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    attrsArray: IntArray,
) : AppCompatButton(
    context,
    attributeSet
) {
    protected val typedArray = context.obtainStyledAttributes(attributeSet, attrsArray)

    protected abstract val resources: ManageAttributeResources

    protected abstract fun updateView()
}

