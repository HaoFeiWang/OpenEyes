package com.whf.openeyes.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.whf.openeyes.R

/**
 * Created by whf on 2018/7/5.
 */
class BriefCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val ivContent = itemView.findViewById<ImageView>(R.id.iv_brief_card_content)
    val tvTitle = itemView.findViewById<TextView>(R.id.tv_brief_card_title)
    val tvDescription = itemView.findViewById<TextView>(R.id.tv_brief_card_description)

}