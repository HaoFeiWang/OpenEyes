package com.whf.openeyes.net

import com.wandoujia.account.dto.AccountResponse
import com.wandoujia.eyepetizer.api.AccountApiResult
import com.wandoujia.eyepetizer.api.ApiResult
import com.wandoujia.eyepetizer.api.result.GetShiedListResult
import com.wandoujia.eyepetizer.api.result.UpdateAvaterResult
import com.wandoujia.eyepetizer.mvp.model.PGCDetailModel
import com.wandoujia.eyepetizer.mvp.model.VideoModel
import com.whf.openeyes.net.bean.Discover
import io.reactivex.Observable
import okhttp3.ad
import retrofit2.http.*

/**
 * Created by whf on 2018/6/30.
 */
interface ApiServer {

    @GET("api/v4/discovery")
    fun getDiscover(): Observable<Discover>

    //HorizontalScrollCard
    @GET("api/v4/discovery/hot")
    fun getHot(): Observable<Any>

    @GET("api/v4/discovery/category")
    fun getCategory(): Observable<Any>

    @GET("api/v4/pgcs/all")
    fun getAuthor(): Observable<Any>

    @GET("api/v4/tabs/follow")
    fun getFollow(): Observable<Any>

    @GET("api/v4/tabs/selected")
    fun getSelected(): Observable<Any>

    //分类列表
    //http://baobab.kaiyanapp.com/api/v5/category/list

    @GET("api/v2/video/{videoId}")
    abstract fun requestSingleVideo(
            @Path("videoId") var1: Int,
            @Query("resourceType") var2: String): rx.Observable<VideoModel>

    @FormUrlEncoded
    @POST("api/v3/share/shareNotify")
    abstract fun shareNotify(@Field("sourceType") var1: Int, @Field("videoId") var2: Long, @Field("result") var4: Int): rx.Observable<ApiResult>


    @FormUrlEncoded
    @POST("api/v2/action/ugc/offline")
    abstract fun ugcDelete(@Field("id") var1: Long, @Field("itemType") var3: String): rx.Observable<ApiResult>

    @FormUrlEncoded
    @POST("api/v2/action/ugc/report")
    abstract fun ugcReport(@Field("id") var1: Long, @Field("itemType") var3: String, @Field("description") var4: String): rx.Observable<ApiResult>

    @FormUrlEncoded
    @POST("api/v4/shield/add")
    abstract fun addShield(@Field("itemType") var1: String, @Field("itemId") var2: Int): rx.Observable<ApiResult>

    @FormUrlEncoded
    @POST("api/v1/follow/cancel")
    abstract fun cancelFollow(@Field("itemType") var1: String, @Field("itemId") var2: Int): rx.Observable<ApiResult>

    @FormUrlEncoded
    @POST("api/v4/shield/cancel")
    abstract fun cancelShield(@Field("itemType") var1: String, @Field("itemId") var2: Int): rx.Observable<ApiResult>

    @FormUrlEncoded
    @POST("api/v1/follow/add")
    abstract fun doFollow(@Field("itemType") var1: String, @Field("itemId") var2: Int): rx.Observable<ApiResult>

    @FormUrlEncoded
    @POST("api/v4/hateVideo/add")
    abstract fun hateVideo(@Field("reason") var1: Int, @Field("videoId") var2: Int, @Field("tagId") var3: Int): rx.Observable<ApiResult>

    @GET
    abstract fun queryMoreShieldUserList(@Url var1: String): rx.Observable<GetShiedListResult>

    @GET("api/v4/shield/list")
    abstract fun queryShieldUserList(@Query("itemType") var1: String, @Query("num") var2: Int): rx.Observable<GetShiedListResult>


    @GET("api/v5/userInfo/tab")
    abstract fun queryInfoAndTab(@Query("id") var1: Long, @Query("userType") var3: String): rx.Observable<PGCDetailModel>

    @FormUrlEncoded
    @POST("v1/api/bindPhone")
    abstract fun bindPhone(@Field("telephone") var1: String, @Field("code") var2: String, @Field("uid") var3: String): rx.Observable<AccountApiResult>

    @FormUrlEncoded
    @POST("v1/api/login")
    abstract fun normalLogin(@Field("username") var1: String, @Field("password") var2: String): rx.Observable<AccountResponse>

    @FormUrlEncoded
    @POST("v2/api/register")
    abstract fun normalRegister(@Field("username") var1: String, @Field("password") var2: String, @Field("code") var3: String): rx.Observable<AccountResponse>

    @FormUrlEncoded
    @POST("v1/api/sms/initialization")
    abstract fun requestVerifyCode(@Field("type") var1: String, @Field("telephone") var2: String): rx.Observable<AccountApiResult>

    @FormUrlEncoded
    @POST("api/pgc/login")
    abstract fun authorLogin(@Field("username") var1: String, @Field("password") var2: String): rx.Observable<AccountResponse>


    @Multipart
    @POST("api/tools/image")
    abstract fun updateAvater(@Part("file\"; filename=\"croped_avatar.jpg\"") var1: ad): rx.Observable<UpdateAvaterResult>

    @Multipart
    @POST("api/tools/image")
    abstract fun updateCover(@Part("file\"; filename=\"croped_cover.jpg\"") var1: ad): rx.Observable<UpdateAvaterResult>

    @FormUrlEncoded
    @POST("api/v5/userInfo/edit")
    abstract fun updateUserInfo(@Field("nick") var1: String, @Field("gender") var2: String, @Field("description") var3: String, @Field("avatar") var4: String, @Field("cover") var5: String): rx.Observable<AccountResponse>
}