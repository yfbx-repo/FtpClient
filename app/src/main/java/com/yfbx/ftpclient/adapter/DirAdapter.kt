package com.yfbx.ftpclient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yfbx.ftpclient.R

/**
 * Author: Edward
 * Date: 2018/12/20
 * Description:
 */


class DirAdapter(data: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_dir, data) {


    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper!!.setText(R.id.dir_txt, item)
    }
}
