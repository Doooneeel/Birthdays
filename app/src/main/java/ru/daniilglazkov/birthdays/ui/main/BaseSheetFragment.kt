package ru.daniilglazkov.birthdays.ui.main

import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.daniilglazkov.birthdays.ui.core.Debounce
import ru.daniilglazkov.birthdays.ui.core.HideKeyboard
import ru.daniilglazkov.birthdays.ui.core.OnClosed
import ru.daniilglazkov.birthdays.ui.core.ProvideViewModel
import ru.daniilglazkov.birthdays.ui.core.click.OnDebouncedClickListener

/**
 * @author Danil Glazkov on 12.06.2022, 20:02
 */
abstract class BaseSheetFragment<VB: ViewBinding, VM: BaseSheetViewModel.Abstract<*>>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val viewModelClass: Class<VM>,
    private val debounce: Debounce = Debounce.NoDelay()
) : BottomSheetDialogFragment(),
    OnClosed.Unit,
    HideKeyboard
{
    protected val binding: VB get() = checkNotNull(_binding)
    protected val viewModel: VM get() = checkNotNull(_viewModel)

    protected val bottomSheetBehavior: BottomSheetBehavior<*> by lazy {
        BottomSheetBehavior.from(view?.parent as View)
    }
    protected val clearFocusViewList = mutableListOf<View>()

    private var _binding: VB? = null
    private var _viewModel: VM? = null
    private var onClosed: () -> Unit = { }

    protected fun View.setOnSingleClick(listener: OnClickListener) {
        setOnClickListener(OnDebouncedClickListener.Base(debounce, listener::onClick))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        binding.root.setOnClickListener { anyView ->
            clearFocusViewList.find { it != anyView }?.run {
                hideKeyboard(anyView.windowToken)
                anyView.clearFocus()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewModel = (requireActivity() as ProvideViewModel).provideViewModel(viewModelClass, this)

        viewModel.addCloseable(onClosed)
        viewModel.observeCloseDialog(this) { dismiss() }
    }

    override fun onClosed(block: () -> Unit) {
        onClosed = block
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun hideKeyboard(windowToken: IBinder) = (requireActivity() as HideKeyboard)
        .hideKeyboard(windowToken)
}