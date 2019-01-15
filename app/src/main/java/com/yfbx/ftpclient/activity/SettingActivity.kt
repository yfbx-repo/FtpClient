package com.yfbx.ftpclient.activity

import android.os.Bundle
import android.text.TextUtils
import com.yfbx.ftpclient.R
import com.yfbx.ftpclient.utils.*
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * Author: Edward
 * Date: 2018/12/21
 * Description:
 */

class SettingActivity : BaseActivity() {

    var downloadPath = getDownloadPath()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setToolbar(getString(R.string.setting), true)

        ipTxt.setText(getIp())
        portTxt.setText(getPort().toString())
        downloadPathTxt.text = downloadPath

        settingOk.setOnClickListener({ v -> settingOk() })
        downloadPathTxt.setOnClickListener({ v -> showDirPicker() })

    }

    /**
     * 选择文件夹
     */
    private fun showDirPicker() {
        FileUtil().showDirectoryPicker(this, {
            downloadPath = it
            downloadPathTxt.text = downloadPath
        })
    }


    /**
     * 保存设置
     */
    private fun settingOk() {
        if (TextUtils.isEmpty(ipTxt.text)) {
            show("请输入IP地址")
            return
        }
        if (TextUtils.isEmpty(portTxt.text)) {
            show("请输入端口")
            return
        }

        saveIp(ipTxt.text.toString())
        savePort(portTxt.text.toString().toInt())
        saveDownloadPath(downloadPath)

        finish()
    }
}