package com.yfbx.ftpclient.ftp

import com.yfbx.ftpclient.utils.getIp
import com.yfbx.ftpclient.utils.getPort
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPFile
import org.apache.commons.net.ftp.FTPReply
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * Author: Edward
 * Date: 2018/12/20
 * Description:
 */

class FTP private constructor() {

    private val client = FTPClient()
    var isConnected = false

    /**
     * 单例模式（双重校验锁）
     */
    companion object {
        val instance: FTP by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            FTP()
        }
    }

    /**
     * 连接FTP
     */
    fun connect(): Boolean {
        try {
            client.connect(getIp(), getPort())
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            close()
        }
        return false
    }


    /**
     * 并登录
     */
    fun login(user: String, password: String) {
        try {
            client.login(user, password)
        } catch (e: Exception) {
            e.printStackTrace()
            close()
            isConnected = false
        }
        isConnected = FTPReply.isPositiveCompletion(client.replyCode)
    }


    /**
     * 创建文件夹
     */
    fun makeDir(dir: String) {
        try {
            client.makeDirectory(dir)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    /**
     * 转到指定目录下
     */
    fun cd(path: String) {
        try {
            client.changeWorkingDirectory(path)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    /**
     * 获取当前目录下所有文件
     */
    fun getFiles(): Array<FTPFile> {
        try {
            return client.listFiles()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return arrayOf()
    }

    /**
     * 上传文件
     */
    fun uploadFile(file: File): Boolean {
        try {
            //设置上传文件需要的一些基本信息
            client.bufferSize = 1024
            client.controlEncoding = "UTF-8"
            client.enterLocalPassiveMode()
            client.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE)

            //文件上传
            val fileInputStream = FileInputStream(file)
            client.storeFile(file.name, fileInputStream)
            fileInputStream.close()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false

    }


    /**
     * 下载
     */
    fun downloadFile(file: FTPFile, localFile: File): Boolean {
        try {
            val outputStream = FileOutputStream(localFile)
            client.retrieveFile(file.name, outputStream)
            outputStream.close()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 退出登陆FTP，关闭ftpClient的连接
     */
    fun close() {
        try {
            if (client.isConnected) {
                client.logout()
                client.disconnect()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        isConnected = false
    }

}