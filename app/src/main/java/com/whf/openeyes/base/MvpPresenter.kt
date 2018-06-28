package com.whf.openeyes.base

/**
 * Created by whf on 2018/6/28.
 */
open abstract class MvpPresenter<V : IView, M : IModel> {

    protected var mView: V? = null
    protected var mModel: M? = null

    protected fun BasePresenter(){
        this.mModel = createModule()
    }

    fun attachView(view:V){
        this.mView = view
    }

    fun detachView(){
        this.mView = null
    }

    protected abstract fun createModule(): M?

    protected fun getModule():M?{
        return mModel
    }
}