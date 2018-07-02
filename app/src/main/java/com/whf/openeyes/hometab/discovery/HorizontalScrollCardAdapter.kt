package com.whf.openeyes.hometab.discovery

import android.content.Context
import android.media.Image
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.whf.openeyes.data.LOG_TAG
import com.whf.openeyes.net.bean.Discovery
import java.time.temporal.TemporalAccessor

/**
 * Created by whf on 2018/7/2.
 */
class HorizontalScrollCardAdapter(val context: Context,
                                  var cardList: List<Discovery.DataItem>) : PagerAdapter() {

    val TAG = LOG_TAG+HorizontalScrollCardAdapter::class.java.simpleName
    val itemViewArray = ArrayList<ImageView>()

    override fun isViewFromObject(view: View, objectView: Any): Boolean {
        return view === objectView
    }

    override fun getCount(): Int {
        Log.d(TAG, "card data = $cardList")
        return cardList.size
    }

    fun updateCardList(cardList: List<Discovery.DataItem>) {
        this.cardList = cardList
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        //TODO 可以使用Anko
        if (itemViewArray.size <= position) {
            itemViewArray.add(ImageView(context))
        }

        val imageView = itemViewArray[position]
        container.addView(imageView)
        Glide.with(imageView).load(cardList[position].data.image).into(imageView)

        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(itemViewArray[position])
    }
}