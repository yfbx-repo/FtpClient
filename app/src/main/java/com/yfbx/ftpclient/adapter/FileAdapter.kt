package com.yfbx.ftpclient.adapter

import android.support.annotation.DrawableRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yfbx.ftpclient.R
import com.yfbx.ftpclient.utils.getFileIcon
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Author: Edward
 * Date: 2018/12/20
 * Description:
 */


class FileAdapter(data: MutableList<File>) : BaseQuickAdapter<File, BaseViewHolder>(R.layout.item_file, data) {


    override fun convert(helper: BaseViewHolder?, item: File?) {
        helper!!.setText(R.id.file_txt, item!!.name)
        item.length()
        val size = if (item.isFile) getSize(item.length()) else ""
        helper.setGone(R.id.size_txt, item.isFile)
        helper.setText(R.id.size_txt, size)
        helper.setText(R.id.time_txt, getTime(item.lastModified()))
        helper.setImageResource(R.id.file_type_img, getIcon(item))
    }

    /**
     * 文件类型图标
     */
    @DrawableRes
    private fun getIcon(item: File): Int {
        return if (item.isDirectory) R.mipmap.ic_directory else getFileIcon(item.name)
    }

    /**
     * 文件大小
     */
    private fun getSize(size: Long): String {
        val GB = size / 1024 / 1024 / 1024
        if (GB >= 1) {
            return "$GB GB"
        }

        val MB = size / 1024 / 1024
        if (MB >= 1) {
            return "$MB MB"
        }

        val KB = size / 1024
        if (KB >= 1) {
            return "$KB KB"
        }
        return "$size B"
    }

    /**
     * 时间
     */
    private fun getTime(mills: Long): String {
        return SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault()).format(mills)
    }

}