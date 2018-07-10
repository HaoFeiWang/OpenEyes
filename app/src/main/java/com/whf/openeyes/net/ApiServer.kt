package com.whf.openeyes.net

import com.whf.openeyes.data.bean.DataList
import io.reactivex.Observable
import retrofit2.http.GET


/**
 * Created by whf on 2018/6/30.
 */
interface ApiServer {

    @GET("v5/index/tab/discovery")
    fun getDiscover(): Observable<DataList>



//    @GET("api/v4/discovery")
//    fun getDiscover(): Observable<Discover>
//
//    //HorizontalScrollCard
//    @GET("api/v4/discovery/hot")
//    fun getHot(): Observable<Any>
//
//    @GET("api/v4/discovery/category")
//    fun getCategory(): Observable<Any>
//
//    @GET("api/v4/pgcs/all")
//    fun getAuthor(): Observable<Any>
//
//    @GET("api/v4/tabs/follow")
//    fun getFollow(): Observable<Any>
//
//    @GET("api/v4/tabs/selected")
//    fun getSelected(): Observable<Any>

    //分类列表
    //http://baobab.kaiyanapp.com/api/v5/category/list

//    @GET("api/v2/video/{videoId}")
//    abstract fun requestSingleVideo(
//            @Path("videoId") var1: Int,
//            @Query("resourceType") var2: String): rx.Observable<VideoModel>
//
//    @FormUrlEncoded
//    @POST("api/v3/share/shareNotify")
//    abstract fun shareNotify(@Field("sourceType") var1: Int, @Field("videoId") var2: Long, @Field("result") var4: Int): rx.Observable<ApiResult>
//
//
//    @FormUrlEncoded
//    @POST("api/v2/action/ugc/offline")
//    abstract fun ugcDelete(@Field("id") var1: Long, @Field("itemType") var3: String): rx.Observable<ApiResult>
//
//    @FormUrlEncoded
//    @POST("api/v2/action/ugc/report")
//    abstract fun ugcReport(@Field("id") var1: Long, @Field("itemType") var3: String, @Field("description") var4: String): rx.Observable<ApiResult>
//
//    @FormUrlEncoded
//    @POST("api/v4/shield/add")
//    abstract fun addShield(@Field("itemType") var1: String, @Field("itemId") var2: Int): rx.Observable<ApiResult>
//
//    @FormUrlEncoded
//    @POST("api/v1/follow/cancel")
//    abstract fun cancelFollow(@Field("itemType") var1: String, @Field("itemId") var2: Int): rx.Observable<ApiResult>
//
//    @FormUrlEncoded
//    @POST("api/v4/shield/cancel")
//    abstract fun cancelShield(@Field("itemType") var1: String, @Field("itemId") var2: Int): rx.Observable<ApiResult>
//
//    @FormUrlEncoded
//    @POST("api/v1/follow/add")
//    abstract fun doFollow(@Field("itemType") var1: String, @Field("itemId") var2: Int): rx.Observable<ApiResult>
//
//    @FormUrlEncoded
//    @POST("api/v4/hateVideo/add")
//    abstract fun hateVideo(@Field("reason") var1: Int, @Field("videoId") var2: Int, @Field("tagId") var3: Int): rx.Observable<ApiResult>
//
//    @GET
//    abstract fun queryMoreShieldUserList(@Url var1: String): rx.Observable<GetShiedListResult>
//
//    @GET("api/v4/shield/list")
//    abstract fun queryShieldUserList(@Query("itemType") var1: String, @Query("num") var2: Int): rx.Observable<GetShiedListResult>
//
//
//    @GET("api/v5/userInfo/tab")
//    abstract fun queryInfoAndTab(@Query("id") var1: Long, @Query("userType") var3: String): rx.Observable<PGCDetailModel>
//
//    @FormUrlEncoded
//    @POST("v1/api/bindPhone")
//    abstract fun bindPhone(@Field("telephone") var1: String, @Field("code") var2: String, @Field("uid") var3: String): rx.Observable<AccountApiResult>
//
//    @FormUrlEncoded
//    @POST("v1/api/login")
//    abstract fun normalLogin(@Field("username") var1: String, @Field("password") var2: String): rx.Observable<AccountResponse>
//
//    @FormUrlEncoded
//    @POST("v2/api/register")
//    abstract fun normalRegister(@Field("username") var1: String, @Field("password") var2: String, @Field("code") var3: String): rx.Observable<AccountResponse>
//
//    @FormUrlEncoded
//    @POST("v1/api/sms/initialization")
//    abstract fun requestVerifyCode(@Field("type") var1: String, @Field("telephone") var2: String): rx.Observable<AccountApiResult>
//
//    @FormUrlEncoded
//    @POST("api/pgc/login")
//    abstract fun authorLogin(@Field("username") var1: String, @Field("password") var2: String): rx.Observable<AccountResponse>
//
//
//    @Multipart
//    @POST("api/tools/image")
//    abstract fun updateAvater(@Part("file\"; filename=\"croped_avatar.jpg\"") var1: ad): rx.Observable<UpdateAvaterResult>
//
//    @Multipart
//    @POST("api/tools/image")
//    abstract fun updateCover(@Part("file\"; filename=\"croped_cover.jpg\"") var1: ad): rx.Observable<UpdateAvaterResult>
//
//    @FormUrlEncoded
//    @POST("api/v5/userInfo/edit")
//    abstract fun updateUserInfo(@Field("nick") var1: String, @Field("gender") var2: String, @Field("description") var3: String, @Field("avatar") var4: String, @Field("cover") var5: String): rx.Observable<AccountResponse>


//    //首页精选
//    http://baobab.kaiyanapp.com/api/v2/feed?
//
////相关视频
//    http://baobab.kaiyanapp.com/api/v4/video/related?id=xxx
//
////获取分类
//    http://baobab.kaiyanapp.com/api/v4/categories
//
////获取分类详情List
//    http://baobab.kaiyanapp.com/api/v4/categories/videoList?id=xxx
//
////获取排行榜的 Info
//    http://baobab.kaiyanapp.com/api/v4/rankList
//
////获取搜索信息
//    http://baobab.kaiyanapp.com/api/v1/search?&num=10&start=10&query=xxx
//
////热门搜索关键词
//    http://baobab.kaiyanapp.com/api/v3/queries/hot
//
////关注
//    http://baobab.kaiyanapp.com/api/v4/tabs/follow
//
////热门搜索词
//    http://baobab.kaiyanapp.com/api/v3/queries/hot
//    ["阅后即瞎","日食记","复仇者联盟","励志","谷阿莫","复仇者联盟3","美食","广告","爱情","舞蹈","搞笑","漫威","动画","日本","电影相关","健身","VR","滑板","脱口秀","寻梦环游记"]
//
//    作者信息
//    http://baobab.kaiyanapp.com/api/v4/pgcs/detail/tab?id=571
}