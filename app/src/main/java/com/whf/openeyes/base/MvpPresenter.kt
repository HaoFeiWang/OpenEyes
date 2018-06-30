package com.whf.openeyes.base

import com.whf.openeyes.data.LOG_TAG

/**
 * Created by whf on 2018/6/28.
 */
open abstract class MvpPresenter<V : IView, M : IModel> {
    protected val TAG = LOG_TAG + this.javaClass.simpleName

    protected var mView: V? = null
    protected val mModel: M = createModule()

    protected fun BasePresenter() {
    }

    fun attachView(view: V) {
        this.mView = view
    }

    fun detachView() {
        this.mView = null
    }

    protected abstract fun createModule(): M

    protected fun getModule(): M? {
        return mModel
    }
}