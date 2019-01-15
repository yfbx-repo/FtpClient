package com.yfbx.ftpclient.activity

import android.os.Bundle
import com.yfbx.ftpclient.R
import com.yfbx.ftpclient.adapter.DirAdapter
import com.yfbx.ftpclient.adapter.FileAdapter
import com.yfbx.ftpclient.utils.sd
import com.yfbx.ftpclient.utils.show
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

/**
 * Author: Edward
 * Date: 2018/12/22
 * Description:本地目录
 */

class LocalActivity : BaseActivity() {

    private val dirs = mutableListOf<String>()
    private val dirAdapter = DirAdapter(dirs)

    private val files = mutableListOf<File>()
    private val fileAdapter = FileAdapter(files)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar(getString(R.string.local_dir), true)

        val path = intent.getStringExtra("path")
        if (path == null) {
            dirs.add("sd")
            files.addAll(File(sd).listFiles())
        } else {
            dirs.add("sd")
            dirs.add(path.substring(sd.length + 1))
            files.addAll(File(path).listFiles())
        }

        dirRecycler.adapter = dirAdapter
        dirAdapter.setOnItemClickListener({ adapter, view, position -> onDirClick(position) })

        fileRecycler.adapter = fileAdapter
        fileAdapter.setOnItemClickListener({ adapter, view, position -> onFileClick(position) })
    }


    /**
     * 点击目录
     */
    private fun onDirClick(position: Int) {
        if (position != dirs.size - 1) {
            //移除position之后的条目
            dirs.removeAll(dirs.subList(position + 1, dirs.size))

            var path = sd
            //从1开始，因为第0个是手动加的"sd",until 不包括最后一个
            for (i in 1 until dirs.size) {
                path += "/${dirs[i]}"
            }
            val file = File(path)
            listFile(file)
        }
    }


    /**
     * 列表点击事件
     */
    private fun onFileClick(position: Int) {
        val file = files[position]
        if (file.isFile) {
            show("查看文件，开发中...")
            //如果是文件,查看
            //TODO 查看文件
        } else {
            //如果是文件夹，则进入文件夹
            dirs.add(file.name)
            listFile(file)
        }
    }

    /**
     * 列出文件夹下所有文件
     */
    private fun listFile(file: File) {
        println("文件路径：${file.path}")
        println("文件名：${file.name}")
        files.clear()
        files.addAll(file.listFiles())
        fileAdapter.notifyDataSetChanged()
        dirAdapter.notifyDataSetChanged()
    }

    /**
     * 返回按键
     */
    override fun onBackPressed() {
        val index = dirs.size - 2
        if (index < 0) {
            super.onBackPressed()
        } else {
            onDirClick(index)
        }
    }
}