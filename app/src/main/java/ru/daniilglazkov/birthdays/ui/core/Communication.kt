package ru.daniilglazkov.birthdays.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * @author Danil Glazkov on 17.06.2022, 08:02
 */
interface Communication {

    interface Observe<T> {
        fun observe(owner: LifecycleOwner, observer: Observer<T>)
    }

    interface Update<T> : Mapper.Unit<T>

    interface Mutable<T> : Observe<T>, Update<T>

    abstract class Abstract<T>(protected val liveData: MutableLiveData<T>) : Mutable<T> {
        protected val value get() = checkNotNull(liveData.value)

        override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
            liveData.observe(owner, observer)
        }
    }

    abstract class Ui<T : Any>(
        liveData: MutableLiveData<T> = MutableLiveData()
    ) : Abstract<T>(liveData) {
        constructor(initValue: T) : this(MutableLiveData(initValue))

        override fun map(source: T) { liveData.value = source }
    }

    abstract class Post<T : Any>(
        liveData: MutableLiveData<T> = MutableLiveData(),
    ) : Abstract<T>(liveData) {
        override fun map(source: T) = liveData.postValue(source)
    }

    abstract class SingleUi<T : Any> : Ui<T>(SingleLiveEvent())
    abstract class SinglePost<T : Any> : Post<T>(SingleLiveEvent())

    class Unit : Mutable<kotlin.Unit> {
        override fun observe(owner: LifecycleOwner, observer: Observer<kotlin.Unit>) = Unit
        override fun map(source: kotlin.Unit) = Unit
    }
}