package ru.daniilglazkov.birthdays.ui.main

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import ru.daniilglazkov.birthdays.databinding.ActivityMainBinding
import ru.daniilglazkov.birthdays.ui.core.FragmentFactory
import ru.daniilglazkov.birthdays.ui.core.HideKeyboard
import ru.daniilglazkov.birthdays.ui.core.ProvideFragmentFactory
import ru.daniilglazkov.birthdays.ui.core.ProvideViewModel

class MainActivity : AppCompatActivity(), ProvideViewModel, ProvideFragmentFactory, HideKeyboard {

    private lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentFactory = FragmentFactory.Base(binding.frame.id, supportFragmentManager)
        val viewModel = provideViewModel(MainViewModel.Base::class.java, this)

        viewModel.observeNavigation(owner = this) {
            fragmentFactory.fragment(it)
        }
        viewModel.init(savedInstanceState == null)
    }

    override fun hideKeyboard(windowToken: IBinder) {
        val inputService: Any = getSystemService(Context.INPUT_METHOD_SERVICE)
        val inputMethodManager: InputMethodManager = (inputService as InputMethodManager)
        inputMethodManager.hideSoftInputFromWindow(windowToken, HIDE_SOFT_INPUT_FLAG)
    }

    override fun fragmentFactory(): FragmentFactory = fragmentFactory

    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T {
        return (application as ProvideViewModel).provideViewModel(clazz, owner)
    }

    companion object {
        private const val HIDE_SOFT_INPUT_FLAG: Int = 0
    }
}