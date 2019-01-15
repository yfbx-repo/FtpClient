package com.yfbx.ftpclient.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.yfbx.ftpclient.R
import com.yfbx.ftpclient.model.FtpModel
import com.yfbx.ftpclient.utils.*
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Author: Edward
 * Date: 2018/12/20
 * Description:
 */

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTranslucentBar()
        setContentView(R.layout.activity_login)

        FileUtil().makeDir(this)

        userTxt.setText(getUser())
        passwordTxt.setText(getPassword())
        savePsdBtn.isChecked = !TextUtils.isEmpty(getPassword())

        loginBtn.setOnClickListener({ login() })
        settingBtn.setOnClickListener({ startActivity(Intent(this, SettingActivity::class.java)) })
    }

    override fun onResume() {
        super.onResume()
        setImmersive()
    }


    /**
     * 登录
     */
    private fun login() {
        if (TextUtils.isEmpty(userTxt.text)) {
            show("请输入用户名")
            return
        }
        if (TextUtils.isEmpty(passwordTxt.text)) {
            show("请输入密码")
            return
        }

        val pd = ProgressDialog(this)
        pd.setMessage("正在登录...")
        pd.show()
        FtpModel().connect(userTxt.text.toString(), passwordTxt.text.toString(), { isConnect, isLogin ->
            pd.dismiss()
            onLogin(isConnect, isLogin)
        })
    }


    private fun onLogin(isConnect: Boolean, isLogin: Boolean) {
        if (!isConnect) {
            show("连接失败")
            return
        }
        if (!isLogin) {
            show("用户名或密码不正确")
            return
        }

        val rememberPsd = if (savePsdBtn.isChecked) passwordTxt.text.toString() else ""
        savePassword(rememberPsd)
        saveUser(userTxt.text.toString())
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        FtpModel().close()
    }

}