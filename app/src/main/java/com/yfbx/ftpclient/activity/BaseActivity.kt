package com.yfbx.ftpclient.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.layout_toolbar.*


/**
 * Author: Edward
 * Date: 2018/12/20
 * Description:
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLandscape(false)
        super.onCreate(savedInstanceState)
    }


    fun setToolbar(title: String, showHomeAsUp: Boolean) {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            supportActionBar!!.setDisplayHomeAsUpEnabled(showHomeAsUp)
        }
        toolbar.setNavigationOnClickListener { v -> onBackPressed() }
        toolbar_title.text = title
    }

    /**
     * 全屏
     */
    fun setFullScreen() {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    /**
     * 屏幕常亮
     */
    fun keepScreenOn() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    /**
     * 固定横竖屏
     */
    fun setLandscape(isLandscape: Boolean) {
        val orientation = if (isLandscape) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        requestedOrientation = orientation
    }

    /**
     * 透明状态栏/导航栏
     */
    fun setTranslucentBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)//设置透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)//设置透明导航栏
    }

    /**
     * 全屏+沉浸式，下拉时状态栏和导航栏浮现(不会挤压布局)，一段时间后自动隐藏
     * 可在onResume()中调用，否则Activity onPause()后会失效
     * 键盘弹出后会失效
     */
    fun setImmersive() {
        val fullScreen = View.SYSTEM_UI_FLAG_FULLSCREEN//全屏:隐藏状态栏
        val hideNavigation = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION//隐藏导航栏
        val immersive = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY//粘性沉浸式:半透明的状态栏和导航栏
        window.decorView.systemUiVisibility = fullScreen or hideNavigation or immersive
    }

}