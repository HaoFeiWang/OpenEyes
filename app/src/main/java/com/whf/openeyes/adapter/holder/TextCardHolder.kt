package com.whf.openeyes.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.whf.openeyes.R

/**
 * Created by whf on 2018/7/3.
 */
class TextCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val layoutHead = itemView.findViewById(R.id.ll_head) as LinearLayout
    val tvHeadContent = itemView.findViewById(R.id.tv_head_content) as TextView
    val ivHeadGo = itemView.findViewById(R.id.iv_head_go) as ImageView

    val layoutFooter = itemView.findViewById(R.id.rl_footer) as RelativeLayout
    val tvFooterContent = itemView.findViewById(R.id.tv_footer_content) as TextView
    val ivFooterGo = itemView.findViewById(R.id.iv_footer_go) as ImageView
}