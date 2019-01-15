package com.yfbx.ftpclient.utils

import android.Manifest
import android.app.Activity
import android.os.Environment
import android.support.v4.app.FragmentActivity
import cn.qqtheme.framework.picker.FilePicker
import com.yfbx.resulthelper.ResultHelper
import java.io.File

/**
 * Author: Edward
 * Date: 2018/12/21
 * Description:
 */


val sd = Environment.getExternalStorageDirectory().path
val ftp = "$sd/FTP"

class FileUtil {


    /**
     * 请求权限，创建本地文件夹
     */
    fun makeDir(activity: FragmentActivity) {
        ResultHelper.with(activity).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, { isGrant ->
            if (isGrant) {
                val file = File(getDownloadPath())
                if (!file.exists()) {
                    file.mkdirs()
                }
            }
        })
    }

    /**
     * 文件选择器
     */
    fun showFilePicker(activity: Activity, listener: (String) -> Unit) {
        val picker = FilePicker(activity, FilePicker.FILE)
        picker.setRootPath(sd)
        picker.setOnFilePickListener { currentPath -> listener.invoke(currentPath) }
        picker.show()
    }

    /**
     * 文件夹选择器
     */
    fun showDirectoryPicker(activity: Activity, listener: (String) -> Unit) {
        val picker = FilePicker(activity, FilePicker.DIRECTORY)
        picker.setRootPath(sd)
        picker.setOnFilePickListener { currentPath -> listener.invoke(currentPath) }
        picker.show()
    }
}