package com.yfbx.ftpclient.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.Gravity
import android.view.WindowManager
import com.yfbx.ftpclient.R


/**
 * Author: Edward
 * Date: 2018/12/22
 * Description:
 */

abstract class BaseDialog(context: Context) : Dialog(context, R.style.Dialog) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        setWindow()
    }

    @LayoutRes
    abstract fun getLayout(): Int


    /**
     * 需要在setContentView()之后调用
     */
    fun setWindow() {
        val params = window.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.gravity = setGravity()
        window.attributes = params
    }

    fun setGravity(): Int {
        return Gravity.CENTER
    }
}