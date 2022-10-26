package ru.daniilglazkov.birthdays.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import ru.daniilglazkov.birthdays.ui.core.view.listener.OnSwipeListener

/**
 * @author Danil Glazkov on 30.09.2022, 21:36
 */
abstract class BaseNotDraggableSheetFragment<VB: ViewBinding, VM: BaseSheetViewModel.Abstract<*>>(
    inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    viewModelClass: Class<VM>,
) : BaseSheetFragment<VB, VM>(
    inflate,
    viewModelClass
) {
    protected abstract val grappleId: Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val grapple = view.findViewById<View>(grappleId)
        bottomSheetBehavior.isDraggable = false

        grapple.setOnTouchListener(OnSwipeListener.Down {
            dismiss()
        })
    }

}