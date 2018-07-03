package com.whf.openeyes.hometab.discovery.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.whf.openeyes.R

/**
 * Created by whf on 2018/7/3.
 */
class TextCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvCard = itemView.findViewById(R.id.tv_text_card_content) as TextView
}