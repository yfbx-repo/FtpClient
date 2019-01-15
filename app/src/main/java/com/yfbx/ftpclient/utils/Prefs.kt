package com.yfbx.ftpclient.utils

import android.content.Context
import android.content.SharedPreferences
import com.yfbx.ftpclient.App.Companion.app

/**
 * Author: Edward
 * Date: 2018/12/20
 * Description:
 */
//lateinit 只用于变量 var，用于只能生命周期流程中进行获取或者初始化的变量
//lazy 只用于常量val,应用于单例模式(if-null-then-init-else-return)，而且仅当变量被第一次调用的时候，委托方法才会执行。
val prefs: SharedPreferences by lazy { app.getSharedPreferences("FTP_PREFS", Context.MODE_PRIVATE) }

/**
 * User
 */
fun saveUser(user: String) {
    prefs.edit().putString("User", user).apply()
}

fun getUser(): String {
    return prefs.getString("User", "")
}

/**
 * Password
 */
fun savePassword(psd: String) {
    prefs.edit().putString("Password", psd).apply()
}

fun getPassword(): String {
    return prefs.getString("Password", "")
}

/**
 * IP
 */
fun saveIp(ip: String) {
    prefs.edit().putString("IP", ip).apply()
}

fun getIp(): String {
    return prefs.getString("IP", "172.26.0.226")
}

/**
 * Port
 */
fun savePort(port: Int) {
    prefs.edit().putInt("Port", port).apply()
}

fun getPort(): Int {
    return prefs.getInt("Port", 21)
}

/**
 * DownloadPath
 */
fun saveDownloadPath(path: String) {
    prefs.edit().putString("DownloadPath", path).apply()
}

fun getDownloadPath(): String {
    return prefs.getString("DownloadPath", ftp)
}