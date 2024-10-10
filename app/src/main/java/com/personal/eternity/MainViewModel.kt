package com.personal.eternity

import android.app.DownloadManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.personal.eternity.bean.Skill
import com.personal.eternity.bean.WuGong
import com.personal.eternity.helper.SkillHelper
import com.personal.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {

    init {
        writeData()
    }

    private fun writeData() {
        viewModelScope.launch(Dispatchers.IO) {
            val sb = StringBuilder()
            val inputStream: InputStream?
            try {
                inputStream = Utils.getApp().resources.assets.open("skill.json")
                val isr = InputStreamReader(inputStream)
                val reader = BufferedReader(isr)
                var jsonLine: String?
                while (reader.readLine().also { jsonLine = it } != null) {
                    sb.append(jsonLine)
                }
                reader.close()
                isr.close()
                inputStream.run { close() }
            } catch (e: IOException) {
                e.localizedMessage?.let {
                    Log.e(TAG, "writeData error $it")
                }
            }
            if (sb.isEmpty()) return@launch
            Log.i(TAG, "writeData finished ")
            val str = sb.toString()
            val list = mutableListOf<Skill>()
            Gson().fromJson(str, WuGong::class.java).apply {
                neigong?.split(",")?.map {
                    Skill(name = it, addition = 1f)
                }?.let { list.addAll(it) }
                jianfa?.split(",")?.map {
                    Skill(name = it, hurt = 100)
                }?.let { list.addAll(it) }


                if (list.isNotEmpty()){
                    SkillHelper.get.putSkill(list)
                }

            }
            print(Thread.currentThread().name)
            withContext(Dispatchers.Main) {

            }
        }
    }



}

