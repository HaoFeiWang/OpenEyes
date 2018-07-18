package com.whf.openeyes.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.whf.openeyes.R

/**
 * Created by whf on 2018/7/9.
 */
class DynamicInfoCardHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    val ivHeadIcon = itemView.findViewById<ImageView>(R.id.iv_dynamic_info_head)
    val tvName = itemView.findViewById<TextView>(R.id.tv_dynamic_info_name)
    val tvAction = itemView.findViewById<TextView>(R.id.tv_dynamic_info_action)
    val tvContent = itemView.findViewById<TextView>(R.id.tv_dynamic_info_content)
    val ivSourceVideo = itemView.findViewById<ImageView>(R.id.iv_dynamic_info_source_video)
    val tvSourceTitle = itemView.findViewById<TextView>(R.id.tv_dynamic_info_source_title)
    val tvSourceClassify = itemView.findViewById<TextView>(R.id.tv_dynamic_info_source_classify)
    val tvTime = itemView.findViewById<TextView>(R.id.tv_dynamic_info_time)
    val tvPraiseNum = itemView.findViewById<TextView>(R.id.tv_dynamic_info_praise_num)
}