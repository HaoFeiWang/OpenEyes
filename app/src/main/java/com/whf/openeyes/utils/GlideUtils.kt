package com.whf.openeyes.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


private val roundOption = RequestOptions().transform(RoundedCorners(10))

private val circleOption = RequestOptions().transform(CircleCrop())

fun ImageView.load(url: String) {
    Glide.with(this).load(url).into(this)
}

fun ImageView.loadCircle(url: String) {
    Glide.with(this).load(url).apply(circleOption).into(this)
}

fun ImageView.loadRound(url: String) {
    Glide.with(this).load(url).apply(roundOption).into(this)
}

fun ImageView.loadRound(url: String, radius: Int) {
    Glide.with(this).load(url)
            .apply(RequestOptions().transform(RoundedCorners(radius)))
            .into(this)
}


