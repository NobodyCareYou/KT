package com.personal.common.bus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import java.util.concurrent.TimeUnit

interface Observable<T> {
    fun post(value: T)
    fun postDelay(value: T, delay: Long)
    fun postDelay(value: T, delay: Long, unit: TimeUnit)
    fun observe(owner: LifecycleOwner, observer: Observer<in T>)
    fun observeSticky(owner: LifecycleOwner, observer: Observer<T>)
    fun observeForever(observer: Observer<in T>)
    fun observeStickyForever(observer: Observer<T>)
    fun removeObserver(observer: Observer<in T>)
}