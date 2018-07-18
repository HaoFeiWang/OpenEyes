package com.whf.openeyes.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.whf.openeyes.R

/**
 * Created by whf on 2018/7/5.
 */
class VideoSmallCardHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    val ivContent = itemView.findViewById(R.id.iv_video_small_content) as ImageView
    val tvTitle = itemView.findViewById(R.id.tv_video_small_title) as TextView
    val tvDescription = itemView.findViewById(R.id.tv_video_small_description) as TextView
}