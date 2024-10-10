package com.personal.common.bus

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.TimeUnit

class BusMutableLiveData<T>(key: String) : MutableLiveData<T>(), Observable<T> {

    private val uiHandler = Handler(Looper.getMainLooper())

//    private var key: String? = key

    //    private BusMutableLiveData(String key) {
//        this.key = key;
//    }

    private inner class PostValueTask(val newValue: T) : Runnable {

        override fun run() {
            value = newValue
        }
    }


    private val isMainThread: Boolean
        get() {
            return Looper.getMainLooper().thread === Thread.currentThread()
        }


    override fun post(value: T) {
        if (isMainThread) setValue(value) else uiHandler.post(PostValueTask(value))
    }

    override fun postDelay(value: T, delay: Long) {
        uiHandler.postDelayed(PostValueTask(value), delay)
    }

    override fun postDelay(value: T, delay: Long, unit: TimeUnit) {
        TODO("Not yet implemented")
    }

    override fun observeSticky(owner: LifecycleOwner, observer: Observer<T>) {

        super.observeForever(observer)
    }

    override fun observeStickyForever(observer: Observer<T>) {

        super.observeForever(observer);
    }


    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val safeCastObserver = SafeCastObserver(observer)
        //保存LifecycleOwner的当前状态
        val lifecycle = owner.lifecycle
        val observerSize = getLifecycleObserverMapSize(lifecycle)
        val currentState = lifecycle.currentState
        val needChangeState = owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        if (needChangeState) {
            setLifecycleState(lifecycle, Lifecycle.State.INITIALIZED)
            setLifecycleObserverMapSize(lifecycle, -1)
        }
        super.observe(owner, observer)
        if (needChangeState) {
            //reset LifecycleOwner state
            setLifecycleState(lifecycle, currentState)
            //reset observer size，因为又添加了一个observer，所以数量+1
            setLifecycleObserverMapSize(lifecycle, observerSize + 1)
            //把Observer置为active
            hookObserverActive(safeCastObserver, true)
        }
        //更改Observer的version
        hookObserverVersion(safeCastObserver)
    }

    private fun setLifecycleState(lifecycle: Lifecycle, state: Lifecycle.State) {
        if (lifecycle is LifecycleRegistry) {
            val declaredField = LifecycleRegistry::class.java.getDeclaredField("mState")
            declaredField.isAccessible = true;
            declaredField.set(lifecycle, state)
        }
    }


    private fun getLifecycleObserverMapSize(lifecycle: Lifecycle): Int {
        val observerMapField = LifecycleRegistry::class.java.getDeclaredField("mObserverMap")
        observerMapField.isAccessible = true
        val mObserverMap = observerMapField[lifecycle]
        val superclass = mObserverMap.javaClass.superclass
        val mSizeField = superclass.getDeclaredField("mSize")
        mSizeField.isAccessible = true
        return mSizeField[mObserverMap] as Int
    }

    private fun setLifecycleObserverMapSize(lifecycle: Lifecycle, size: Int) {
        val observerMapField = LifecycleRegistry::class.java.getDeclaredField("mObserverMap")
        observerMapField.isAccessible = true
        val mObserverMap = observerMapField[lifecycle]
        val superclass = mObserverMap.javaClass.superclass
        val mSizeField = superclass.getDeclaredField("mSize")
        mSizeField.isAccessible = true
        mSizeField.set(observerMapField, size)
    }


    private fun hookObserverVersion(observer: Observer<in T>) {
        try {
            //get wrapper's version
            val objectWrapper: Any = getObserverWrapper(observer) ?: return
            val classObserverWrapper = objectWrapper.javaClass.superclass
            val fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion")
            fieldLastVersion.isAccessible = true
            //get livedata's version
            val fieldVersion = LiveData::class.java.getDeclaredField("mVersion")
            fieldVersion.isAccessible = true
            val objectVersion = fieldVersion[this]
            //set wrapper's version
            fieldLastVersion[objectWrapper] = objectVersion
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun hookObserverActive(observer: Observer<in T>, active: Boolean) {
        try {
            //get wrapper's version
            val objectWrapper: Any = getObserverWrapper(observer) ?: return
            val classObserverWrapper = objectWrapper.javaClass.superclass
            val mActive = classObserverWrapper.getDeclaredField("mActive")
            mActive.isAccessible = true
            mActive[objectWrapper] = active
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(java.lang.Exception::class)
    private fun getObserverWrapper(observer: Observer<in T>): Any? {
        val fieldObservers = LiveData::class.java.getDeclaredField("mObservers")
        fieldObservers.isAccessible = true
        val objectObservers = fieldObservers[this]
        val classObservers: Class<*> = objectObservers.javaClass
        val methodGet = classObservers.getDeclaredMethod("get", Any::class.java)
        methodGet.isAccessible = true
        val objectWrapperEntry = methodGet.invoke(objectObservers, observer)
        var objectWrapper: Any? = null
        if (objectWrapperEntry is Map.Entry<*, *>) {
            objectWrapperEntry.value.also { objectWrapper = it }
        }
        return objectWrapper
    }

}