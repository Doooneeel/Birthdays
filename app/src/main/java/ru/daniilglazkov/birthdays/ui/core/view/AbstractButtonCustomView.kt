package ru.daniilglazkov.birthdays.ui.core.view

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
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
    protected abstract val handleOnSaveInstanceState: (Bundle) -> Unit
    protected abstract val handleOnRestoreInstanceState: (Bundle) -> Unit

    protected abstract fun updateView()

    override fun onSaveInstanceState(): Parcelable = Bundle().also { bundle ->
        bundle.putParcelable(PARCELABLE_KEY, super.onSaveInstanceState())
        handleOnSaveInstanceState.invoke(bundle)
    }

    override fun onRestoreInstanceState(state: Parcelable): Unit = (state as Bundle).let {
        super.onRestoreInstanceState(state.getParcelable(PARCELABLE_KEY))
        handleOnRestoreInstanceState.invoke(state)
        updateView()
    }

    companion object {
        private const val PARCELABLE_KEY = "parcelableKey"
    }
}

