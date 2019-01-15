package com.yfbx.ftpclient.activity

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.yfbx.ftpclient.R
import com.yfbx.ftpclient.adapter.DirAdapter
import com.yfbx.ftpclient.adapter.FtpFileAdapter
import com.yfbx.ftpclient.dialog.showNewDirDialog
import com.yfbx.ftpclient.model.FtpModel
import com.yfbx.ftpclient.utils.FileUtil
import com.yfbx.ftpclient.utils.getDownloadPath
import com.yfbx.ftpclient.utils.show
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.commons.net.ftp.FTPFile


class MainActivity : BaseActivity() {

    private var currentPath: String = "/"

    private val dirs = mutableListOf<String>()
    private val dirAdapter = DirAdapter(dirs)

    private val files = mutableListOf<FTPFile>()
    private val fileAdapter = FtpFileAdapter(files)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar(getString(R.string.app_name), false)

        dirs.add("/")

        dirRecycler.adapter = dirAdapter
        fileRecycler.adapter = fileAdapter

        dirAdapter.setOnItemClickListener({ adapter, view, position -> onDirClick(position) })
        fileAdapter.setOnItemClickListener({ adapter, view, position -> onFileClick(position) })
        listFiles(null)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.upload -> {//上传
                FileUtil().showFilePicker(this, {
                    upload(it)
                })
            }
            R.id.download_dir -> {//下载目录
                val intent = Intent(this, LocalActivity::class.java)
                intent.putExtra("path", getDownloadPath())
                startActivity(intent)
            }
            R.id.local_dir -> {//本地目录
                startActivity(Intent(this, LocalActivity::class.java))
            }
            R.id.make_dir -> {//创建文件夹
                makeDir()
            }
            R.id.logout -> {//退出登录
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        return true
    }


    /**
     * 点击目录
     */
    private fun onDirClick(position: Int) {
        if (position != dirs.size - 1) {
            dirs.removeAll(dirs.subList(position + 1, dirs.size))
            currentPath = "/"
            for (i in 1 until dirs.size) {
                currentPath += "/${dirs[i]}"
            }
            listFiles(currentPath)
        }
    }

    /**
     * 列表点击事件
     */
    private fun onFileClick(position: Int) {
        val file = files[position]
        if (file.isFile) {
            //如果是文件,下载文件
            download(file)
        } else {
            //如果是文件夹，则进入文件夹
            currentPath += "/${file.name}"
            listFiles(file.name)
            dirs.add(file.name)
        }
    }

    /***
     * 列出文件夹下所有文件
     */
    private fun listFiles(fileName: String?) {
        FtpModel().listFiles(fileName, {
            files.clear()
            files.addAll(it)
            dirAdapter.notifyDataSetChanged()
            fileAdapter.notifyDataSetChanged()
        })
    }

    /**
     * 新建文件夹
     */
    private fun makeDir() {
        showNewDirDialog(this, {
            FtpModel().makeDir("$currentPath/$it", { listFiles(currentPath) })
        })
    }

    /**
     * 下载
     */
    private fun download(file: FTPFile) {
        FtpModel().download(file, { show(it, Toast.LENGTH_LONG) })
    }

    /**
     * 上传
     */
    private fun upload(local: String) {
        FtpModel().upload(local, {
            listFiles(currentPath)
            show(it, Toast.LENGTH_LONG)
        })
    }


    /**
     * 返回按键，返回上一层文件夹
     */
    override fun onBackPressed() {
        val index = dirs.size - 2
        if (index < 0) {
            exitAlert()
        } else {
            onDirClick(index)
        }
    }

    /**
     * 退出提示
     */
    private fun exitAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("确认退出FTP客户端?")
        builder.setPositiveButton("确定退出", { _, _ -> exit() })
        builder.setNegativeButton("取消", null)
        builder.show()
    }


    private fun exit() {
        finish()
        Process.killProcess(Process.myPid())
    }

    override fun onDestroy() {
        super.onDestroy()
        FtpModel().close()
    }

}
