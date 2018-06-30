package com.whf.openeyes.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by whf on 2018/6/30.
 */
object HttpClient {

    //使用const修饰的属性不会在静态代码块中再次赋值
    private const val BASE_RUL = "http://baobab.kaiyanapp.com/"

    val server:ApiServer by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { getApiServer() }

    private fun getApiServer(): ApiServer {
        return getRetrofit().create(ApiServer::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_RUL)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .build()
    }
}