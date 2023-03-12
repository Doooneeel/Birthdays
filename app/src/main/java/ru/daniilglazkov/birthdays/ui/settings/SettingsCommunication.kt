package ru.daniilglazkov.birthdays.ui.settings

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 02.08.2022, 02:31
 */
interface SettingsCommunication: Communication.Mutable<SettingsUi> {

    interface Observe {
        fun observeSettings(owner: LifecycleOwner, observer: Observer<SettingsUi>)
    }

    class Base : Communication.Post<SettingsUi>(), SettingsCommunication
}