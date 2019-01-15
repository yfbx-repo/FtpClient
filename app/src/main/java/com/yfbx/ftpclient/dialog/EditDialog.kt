package com.yfbx.ftpclient.dialog

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import com.yfbx.ftpclient.R
import kotlinx.android.synthetic.main.dialog_edit.*

/**
 * Author: Edward
 * Date: 2018/12/22
 * Description:
 */

/**
 * 新建文件夹
 */
fun showNewDirDialog(context: Context, listener: (String) -> Unit) {
    EditDialog(context, "新建文件夹", listener).show()
}

class EditDialog private constructor(context: Context) : BaseDialog(context) {

    var title: String = ""
    lateinit var listener: (String) -> Unit

    constructor(context: Context, title: String, listener: (String) -> Unit) : this(context) {
        this.title = title
        this.listener = listener
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialogEditTitle.text = title

        dialogEditCancel.setOnClickListener { dismiss() }
        dialogEditOk.setOnClickListener {
            if (!TextUtils.isEmpty(dialogEditTxt.text)) {
                listener.invoke(dialogEditTxt.text.toString())
                dismiss()
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.dialog_edit
    }
}