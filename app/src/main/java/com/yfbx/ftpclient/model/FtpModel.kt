package com.yfbx.ftpclient.model

import android.os.Handler
import android.os.Looper
import com.yfbx.ftpclient.ftp.FTP
import com.yfbx.ftpclient.utils.getDownloadPath
import org.apache.commons.net.ftp.FTPFile
import java.io.File
import kotlin.concurrent.thread

/**
 * Author: Edward
 * Date: 2018/12/21
 * Description:
 */

class FtpModel {

    /**
     * 连接FTP服务器，并登录
     */
    fun connect(user: String, password: String, listener: (Boolean, Boolean) -> Unit) {
        thread {
            val connect = FTP.instance.connect()
            FTP.instance.login(user, password)
            val isLogin = FTP.instance.isConnected
            Handler(Looper.getMainLooper()).post { listener.invoke(connect, isLogin) }
        }
    }


    /**
     * 上传
     */
    fun upload(local: String, listener: (String) -> Unit) {
        thread {
            val isSuccess = FTP.instance.uploadFile(File(local))
            val msg = if (isSuccess) "上传成功" else "上传失败"
            Handler(Looper.getMainLooper()).post { listener.invoke(msg) }
        }
    }


    /**
     * 下载
     */
    fun download(file: FTPFile, listener: (String) -> Unit) {
        thread {
            val local = File(getDownloadPath(), file.name)
            val isSuccess = FTP.instance.downloadFile(file, local)
            val msg = if (isSuccess) "下载成功：${local.path}" else "下载失败"
            Handler(Looper.getMainLooper()).post { listener.invoke(msg) }
        }
    }

    /***
     * 列出文件夹下所有文件
     */
    fun listFiles(fileName: String?, listener: (Array<FTPFile>) -> Unit) {
        thread {
            if (fileName != null) {
                FTP.instance.cd(fileName)
            }
            val files = FTP.instance.getFiles()
            Handler(Looper.getMainLooper()).post { listener.invoke(files) }
        }
    }

    /**
     * 新建文件夹
     */
    fun makeDir(name: String, listener: () -> Unit) {
        thread {
            FTP.instance.makeDir(name)
            Handler(Looper.getMainLooper()).post { listener.invoke() }
        }
    }

    /**
     * 关闭
     */
    fun close() {
        thread {
            FTP.instance.close()
        }
    }

}