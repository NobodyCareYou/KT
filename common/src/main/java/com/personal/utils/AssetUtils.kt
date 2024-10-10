package com.personal.utils

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class AssetUtils {

    companion object {

        /**
         * 从assets目录中复制整个文件夹内容
         * @param  assetsFileName  String  assets中的文件名 如a.json
         * @param  newPath  String  复制后路径  如：xx:/bb/cc
         */
        fun copyFilesFassets(assetsFileName: String, newPath: String) {
            try {
                val `is`: InputStream = Utils.getApp().assets.open(assetsFileName)
                val fos = FileOutputStream(File(newPath))
                val buffer = ByteArray(1024)
                var byteCount: Int
                while (`is`.read(buffer).also { byteCount = it } != -1) {
                    fos.write(buffer, 0, byteCount)
                }
                fos.flush()
                `is`.close()
                fos.close()
            } catch (e: Exception) {
                e.printStackTrace()

            }
        }
    }
}