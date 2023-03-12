package ru.daniilglazkov.birthdays.ui.main

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import ru.daniilglazkov.birthdays.databinding.ActivityMainBinding
import ru.daniilglazkov.birthdays.ui.core.HideKeyboard
import ru.daniilglazkov.birthdays.ui.core.ProvideViewModel
import ru.daniilglazkov.birthdays.ui.core.navigation.ManageScreen
import ru.daniilglazkov.birthdays.ui.core.navigation.ProvideManageScreen

class MainActivity : AppCompatActivity(), ProvideViewModel, ProvideManageScreen, HideKeyboard {

    private lateinit var manageScreen: ManageScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = provideViewModel(MainViewModel.Base::class.java, this)

        manageScreen = ManageScreen.Base(binding.frame.id, supportFragmentManager)

        viewModel.observeNavigation(owner = this) {
            manageScreen.show(it)
        }
        viewModel.init(savedInstanceState == null)
    }

    override fun hideKeyboard(windowToken: IBinder) {
        val inputService: Any = getSystemService(Context.INPUT_METHOD_SERVICE)
        val inputMethodManager: InputMethodManager = (inputService as InputMethodManager)
        inputMethodManager.hideSoftInputFromWindow(windowToken, HIDE_SOFT_INPUT_FLAG)
    }

    override fun provideManageScreen(): ManageScreen = manageScreen

    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T {
        return (application as ProvideViewModel).provideViewModel(clazz, owner)
    }

    companion object {
        private const val HIDE_SOFT_INPUT_FLAG: Int = 0
    }
}