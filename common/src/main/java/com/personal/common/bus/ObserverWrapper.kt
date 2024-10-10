package com.personal.common.bus

import androidx.lifecycle.Observer

class ObserverWrapper<T>(observer: Observer<T>) : Observer<T> {
    private val observer: Observer<T>

    init {
        this.observer = observer
    }

    override fun onChanged(value: T) {
        if (isCallOnObserve) {
            return
        }
        try {
            observer.onChanged(value)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    private val isCallOnObserve: Boolean
        get() {
            val stackTrace = Thread.currentThread().getStackTrace()
            if (stackTrace.isEmpty()) return false;
            for (element in stackTrace) {
                if ("androidx.lifecycle.LiveData" == element.className && "observeForever" == element.methodName) {
                    return true
                }
            }
            return false
        }
}