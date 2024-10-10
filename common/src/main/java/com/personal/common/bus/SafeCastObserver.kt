package com.personal.common.bus

import androidx.lifecycle.Observer

class SafeCastObserver<T>(observer: Observer<T>) : Observer<T> {

    private val observer: Observer<T>

    init {
        this.observer = observer
    }

    override fun onChanged(value: T) {
        try {
            observer.onChanged(value)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }
}