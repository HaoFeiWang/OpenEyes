package com.whf.openeyes.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import com.whf.openeyes.data.LOG_TAG


private val roundOption = RequestOptions()
        .transform(MultiTransformation(CenterCrop(), RoundedCorners(10)))

private val circleOption = RequestOptions()
        .transform(MultiTransformation(CenterCrop(), CircleCrop()))

fun ImageView.load(requestManager: RequestManager, url: String) {
    requestManager.load(url).into(this)
}

fun ImageView.loadCircle(requestManager: RequestManager, url: String) {
    requestManager.load(url).apply(circleOption).into(this)
}

fun ImageView.loadRound(requestManager: RequestManager, url: String) {
    requestManager.load(url).apply(roundOption).into(this)
}

fun ImageView.loadRound(requestManager: RequestManager, url: String, radius: Int) {
    requestManager.load(url)
            .apply(RequestOptions().transform(RoundedCorners(radius)))
            .into(this)
}

fun View.loadRoundBackground(requestManager: RequestManager, url: String) {
    requestManager
            .asDrawable()
            .load(url)
            .apply(roundOption)
            .into(object : ViewTarget<View,Drawable>(this){
                override fun onResourceReady(resource: Drawable?,
                                             transition: Transition<in Drawable>?) {
                    Log.d(LOG_TAG, "resource ready")
                    getView()?.background = resource
                }
            })
}



