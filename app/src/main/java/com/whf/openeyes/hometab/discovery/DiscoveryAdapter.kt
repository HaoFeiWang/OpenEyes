package com.whf.openeyes.hometab.discovery

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.whf.openeyes.R
import com.whf.openeyes.data.LOG_TAG
import com.whf.openeyes.data.Type
import com.whf.openeyes.net.bean.Discovery

/**
 * Created by whf on 2018/7/2.
 */
class DiscoveryAdapter(var discoveryList: List<Discovery.DiscoveryItem>,
                       private val context: Context) : RecyclerView.Adapter<DiscoveryHolder>() {

    val TAG = LOG_TAG+DiscoveryAdapter::class.java.simpleName
    val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoveryHolder {
        when(viewType){
            1 -> return DiscoveryHolder(layoutInflater.inflate(
                    R.layout.item_discovery_horizontal_card,
                    parent, false))
        }
        return DiscoveryHolder(layoutInflater.inflate(R.layout.item_discovery_horizontal_card,parent,false))
     }

    override fun getItemCount(): Int {
        return discoveryList.size
    }

    override fun onBindViewHolder(holder: DiscoveryHolder, position: Int) {

        val curItem = discoveryList[position]
        Log.d(TAG, "cur item = $curItem")

        when(holder){
            holder.horizontalScrollCard?.let {
                it.adapter = HorizontalScrollCardAdapter(context,curItem.data.itemList)
            }
//            is DiscoveryHolder -> holder.horizontalScrollCard?.adapter?.let {
//                it as HorizontalScrollCardAdapter
//                it.updateCardList(curItem.data.itemList)
//            }?: HorizontalScrollCardAdapter(context,curItem.data.itemList)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getType(discoveryList[position].type)
    }

    private fun getType(type: String):Int {
        when (type) {
            Type.HORIZONTAL_SCROLL_CARD -> return 1
        }
        return 0
    }
}