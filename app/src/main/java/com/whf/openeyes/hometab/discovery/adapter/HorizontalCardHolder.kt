package com.whf.openeyes.hometab.discovery.adapter

import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.whf.openeyes.R

/**
 * Created by whf on 2018/7/2.
 */
class HorizontalCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val horizontalScrollCard = itemView.findViewById(R.id.vp_horizontal_scroll_card) as ViewPager
}