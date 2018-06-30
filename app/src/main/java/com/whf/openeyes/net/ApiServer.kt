package com.whf.openeyes.net

import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by whf on 2018/6/30.
 */
interface ApiServer{

    @GET("api/v4/discovery")
    fun getDiscover():Observable<Any>

}