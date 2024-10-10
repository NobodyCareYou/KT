package com.personal.eternity

import android.app.Application
import android.util.Log
import com.personal.eternity.helper.SkillHelper
import com.personal.utils.Utils
import io.objectbox.android.Admin

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        if (BuildConfig.DEBUG) {
            val started = Admin(SkillHelper.INSTANCE)
                .start(this)
            Log.i("ObjectBoxAdmin", "Started: $started")
        }
    }
}