package ru.daniilglazkov.birthdays.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.core.Mapper

/**
 * @author Danil Glazkov on 17.06.2022, 08:02
 */
interface Communication {

    interface Observe<T : Any> {
        fun observe(owner: LifecycleOwner, observer: Observer<T>)
    }

    interface Update<T : Any> : Mapper.Unit<T>

    interface Mutable<T : Any> : Observe<T>, Update<T>

    abstract class Abstract<T : Any>(protected val liveData: MutableLiveData<T>) : Mutable<T> {
        protected val value get() = checkNotNull(liveData.value)

        override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
            liveData.observe(owner, observer)
        }
    }

    abstract class UiUpdate<T : Any>(
        liveData: MutableLiveData<T> = MutableLiveData()
    ) : Abstract<T>(liveData) {
        constructor(initValue: T) : this(MutableLiveData(initValue))

        override fun map(source: T) { liveData.value = source }
    }

    abstract class PostUpdate<T : Any>(
        liveData: MutableLiveData<T> = MutableLiveData(),
    ) : Abstract<T>(liveData) {
        override fun map(source: T) = liveData.postValue(source)
    }

    abstract class SingleUiUpdate<T : Any> : UiUpdate<T>(SingleLiveEvent())
    abstract class SinglePostUpdate<T : Any> : PostUpdate<T>(SingleLiveEvent())

    class Unit : Mutable<kotlin.Unit> {
        override fun observe(owner: LifecycleOwner, observer: Observer<kotlin.Unit>) = Unit
        override fun map(source: kotlin.Unit) = Unit
    }
}