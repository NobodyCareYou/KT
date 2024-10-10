package com.personal.common.bus

class LiveBus private constructor() {

    private var bus: MutableMap<String, BusMutableLiveData<Any>> = HashMap()

    companion object {
        private val DEFAULT_BUS by lazy { LiveBus() }

        fun <T> get(key: String, type: Class<T>): Observable<T> {
            return DEFAULT_BUS.with(key, type)
        }
    }


    @Synchronized
    fun <T> with(key: String, type: Class<T>): Observable<T> {
        if (!bus.containsKey(key)) {
            bus[key] = BusMutableLiveData(key)
        }
        return bus[key] as Observable<T>
    }


}