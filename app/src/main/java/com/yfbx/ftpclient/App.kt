package com.yfbx.ftpclient

import android.app.Application
import kotlin.properties.Delegates

/**
 * Author: Edward
 * Date: 2018/12/20
 * Description:
 */

class App : Application() {

    companion object {
        var app: App by Delegates.notNull()

    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}