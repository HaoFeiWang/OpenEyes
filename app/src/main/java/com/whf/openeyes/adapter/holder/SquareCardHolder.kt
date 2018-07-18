package com.whf.openeyes.adapter.holder

import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.whf.openeyes.R

/**
 * Created by whf on 2018/7/6.
 */
class SquareCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTitle = itemView.findViewById<TextView>(R.id.tv_square_card_title)
    val vpContent = itemView.findViewById<ViewPager>(R.id.vp_square_card_content)
}