package com.yfbx.ftpclient.utils

import android.support.annotation.DrawableRes
import com.yfbx.ftpclient.R

/**
 * Author: Edward
 * Date: 2018/12/21
 * Description:
 */

val imgType = arrayOf(".jpg", ".jpeg", ".png", ".bmp", ".wbmp", ".webp")
val audioType = arrayOf(".mp3", ".mpga", ".m4a", ".wav", ".amr", ".awb", ".wma", ".ogg", ".oga", ".aac", ".mka")
val videoType = arrayOf(".mpeg", ".mpg", ".mp4", ".m4v", ".3gp", ".3gpp", ".3g2", ".3gpp2", ".mkv", ".webm", ".ts", ".avi", ".wmv", ".asf")
val docType = arrayOf(".doc", ".docx")
val xlsType = arrayOf(".xls", ".xlsx")
val pptType = arrayOf(".ppt", ".pptx")
val htmlType = arrayOf(".html", ".htm")
val pdfType = arrayOf(".pdf")
val txtType = arrayOf(".txt")
val zipType = arrayOf(".zip", ".rar", ".7z", ".iso", ".tar", ".cab")


@DrawableRes
fun getFileIcon(fileName: String): Int {
    if (isImage(fileName)) {
        return R.mipmap.type_img
    }
    if (isVideo(fileName)) {
        return R.mipmap.type_video
    }
    if (isAudio(fileName)) {
        return R.mipmap.type_audio
    }
    if (isTxt(fileName)) {
        return R.mipmap.type_txt
    }
    if (isDoc(fileName)) {
        return R.mipmap.type_doc
    }
    if (isExcel(fileName)) {
        return R.mipmap.type_xls
    }
    if (isPpt(fileName)) {
        return R.mipmap.type_ppt
    }
    if (isPdf(fileName)) {
        return R.mipmap.type_pdf
    }
    if (isHtml(fileName)) {
        return R.mipmap.type_html
    }
    if (isZip(fileName)) {
        return R.mipmap.type_zip
    }
    return R.mipmap.type_unknown
}


fun isImage(fileName: String): Boolean {
    return findType(fileName, imgType)
}

fun isAudio(fileName: String): Boolean {
    return findType(fileName, audioType)
}

fun isVideo(fileName: String): Boolean {
    return findType(fileName, videoType)
}

fun isDoc(fileName: String): Boolean {
    return findType(fileName, docType)
}

fun isExcel(fileName: String): Boolean {
    return findType(fileName, xlsType)
}

fun isPpt(fileName: String): Boolean {
    return findType(fileName, pptType)
}

fun isPdf(fileName: String): Boolean {
    return findType(fileName, pdfType)
}

fun isHtml(fileName: String): Boolean {
    return findType(fileName, htmlType)
}

fun isTxt(fileName: String): Boolean {
    return findType(fileName, txtType)
}

fun isZip(fileName: String): Boolean {
    return findType(fileName, zipType)
}

fun findType(fileName: String, types: Array<String>): Boolean {
    val index = fileName.lastIndexOf(".")
    if (index < 0) {
        return false
    }
    val fileType = fileName.substring(index).toLowerCase()
    return types.contains(fileType)
}