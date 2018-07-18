package com.whf.openeyes.base

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import com.whf.openeyes.data.LOG_TAG

/**
 * Created by whf on 2018/6/30.
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(){
    protected val TAG = LOG_TAG + this.javaClass.simpleName
}