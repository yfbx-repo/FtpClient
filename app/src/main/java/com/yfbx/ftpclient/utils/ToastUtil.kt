package com.yfbx.ftpclient.utils

import android.support.annotation.StringRes
import android.widget.Toast
import com.yfbx.ftpclient.App.Companion.app

/**
 * Author: Edward
 * Date: 2018/12/20
 * Description:
 */

val toast: Toast by lazy { Toast.makeText(app, "", Toast.LENGTH_SHORT) }


fun show(msg: String) {
    toast.setText(msg)
    toast.duration = Toast.LENGTH_SHORT
    toast.show()
}

fun show(@StringRes msg: Int) {
    toast.setText(msg)
    toast.duration = Toast.LENGTH_SHORT
    toast.show()
}

fun show(msg: String, duration: Int) {
    toast.setText(msg)
    toast.duration = duration
    toast.show()
}