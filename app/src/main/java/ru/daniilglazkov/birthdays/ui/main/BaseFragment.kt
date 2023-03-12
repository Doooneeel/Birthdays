package ru.daniilglazkov.birthdays.ui.main

import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.daniilglazkov.birthdays.ui.core.Debounce
import ru.daniilglazkov.birthdays.ui.core.HideKeyboard
import ru.daniilglazkov.birthdays.ui.core.ProvideViewModel
import ru.daniilglazkov.birthdays.ui.core.navigation.NavigationScreen
import ru.daniilglazkov.birthdays.ui.core.navigation.ProvideManageScreen
import ru.daniilglazkov.birthdays.ui.core.view.click.DebouncedOnClickListener

/**
 * @author Danil Glazkov on 10.06.2022, 01:09
 */
abstract class BaseFragment<VB: ViewBinding, VM: BaseViewModel.Abstract>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val viewModelClass: Class<VM>,
) : Fragment(), HideKeyboard {

    protected open val clickDebounce: Debounce = Debounce.NoDelay()

    protected val viewModel: VM get() = checkNotNull(_viewModel)
    protected val binding: VB get() = checkNotNull(_binding)

    private var _binding: VB? = null
    private var _viewModel: VM? = null

    protected fun View.setDebouncedOnClickListener(listener: View.OnClickListener) {
        setOnClickListener(DebouncedOnClickListener.Base(clickDebounce, listener::onClick))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewModel = (requireActivity() as ProvideViewModel).provideViewModel(
            clazz = viewModelClass,
            owner = this
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val showFragment = (requireActivity() as ProvideManageScreen).provideManageScreen()

        viewModel.observeNavigation(owner = this) { screen: NavigationScreen ->
            showFragment.show(screen)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun hideKeyboard(windowToken: IBinder) = (requireActivity() as HideKeyboard)
        .hideKeyboard(windowToken)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}