package com.whf.openeyes.net

import android.text.TextUtils
import android.util.Log
import com.whf.openeyes.data.LOG_TAG
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by whf on 2018/6/30.
 */
object HttpClient {
    private val TAG = LOG_TAG + HttpClient::class.java.simpleName

    //使用const修饰的属性不会在静态代码块中再次赋值
    private const val BASE_RUL = "http://baobab.kaiyanapp.com/api/"

    val server: ApiServer by lazy { getApiServer() }

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

    fun getRelativeUrl(absoluteUrl: String): String {
        val relativeUrl = absoluteUrl.replace(BASE_RUL, "", false)
        Log.d(TAG, "relative url = $relativeUrl")
        return relativeUrl
    }

}