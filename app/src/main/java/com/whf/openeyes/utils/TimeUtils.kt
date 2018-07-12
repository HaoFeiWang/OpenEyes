package com.whf.openeyes.utils

/**
 * Created by whf on 2018/7/13.
 */
fun formatDuration(duration: Int): String {
    var minute: String = (duration / 60).toString()
    var second: String = (duration % 60).toString()

    if (minute.length == 1) {
        minute = "0$minute"
    }

    if (second.length == 1) {
        second = "0$second"
    }
    return "$minute:$second"
}