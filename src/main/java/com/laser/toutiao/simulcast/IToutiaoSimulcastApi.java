package com.laser.toutiao.simulcast;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IToutiaoSimulcastApi
{
    /*正式上线请将 test 换为online*/
    String appendix = "test";

    @GET("http://openapi.look.360.cn/v2/access_token")
    public Call<ResponseBody> getToken(
            @Query("ts") long ts/*时间戳，请传10位秒级时间戳*/,
            @Query("rn") String rn/*随机字符串，由英文大小写字母、数字组成，最长16位*/,
            @Query("ap") String ap/*应用平台分配的appid*/,
            @Query("sign") String sign/*应用平台分配的appkey*/,
            @Query("pn") String pn/*应用包名，SDK打包时自动生成*/,
            @Query("chc") String chc/*本次请求的校验码*/,
            @Query("u") String u/*用户id，一般是32位的MD5值，报表统计用户相关的数据使用*/,
            @Query("version") String version/*客户端应用版本*/,
            @Query("os") String os/*操作系统，支持"android","ios"*/);

    @GET("http://openapi.look.360.cn/v2/list")
    public Call<ResponseBody> getInfoFlow(
                                              @Query("u") String u/*用户id，一般是32位的MD5值，报表统计用户相关的数据使用*/,
                                              @Query("n") String n/*获取新闻条数， 最大为10*/,
                                              @Query("sign") String sign/*应用平台分配的appkey*/,
                                              @Query("access_token") String access_token,
                                              @Query("c") String c/*垂直频道名称，可通过获取频道接口查看所有频道。常用的有：推荐=youlike  视频=video  热点=newhot更多请查看附录1*/,
                                              @Query("f") String f/*指定返回格式json, jsonp*/,
                                              @Query("version") String version/*客户端应用版本*/,
                                              @Query("device") String device/*设备名称, 用户设备标识，0为安卓，1为iOS，2为PC*/,
                                              @Query("sv") String sv/*数据类型控制，1 为基础数据类型*/,
                                              @Query("action") String action/*1下拉; 2上划，结束后有历史数据*/,
                                              @Query("usid") String usid/*一次生存周期session内的ID    定义：应用进程杀掉重启以后生成usid，进程被推到后台6min以后重新生成usid*/);

    /**返回数据中的字段，可以支撑统计需要的字段 */
    @GET("http://openapi.look.360.cn/srv/c")
    public Call<ResponseBody> report(
                                                 @Query("uid") String u/*用户id，一般是32位的MD5值，报表统计用户相关的数据使用*/,
                                                 @Query("url") String url/*当点击链接时取链接地址，但注意urlencode， 回传*/,
                                                 @Query("sign") String sign/*应用平台分配的appkey*/,
                                                 @Query("version") String version/*客户端应用版本号*/,
                                                 @Query("device") String device/*设备名称, 用户设备标识，0为安卓，1为iOS，2为PC*/,
                                                 @Query("channel") String channel/*频道*/,
                                                 @Query("a") String a/*数据类型，新闻自带，回传*/,
                                                 @Query("c") String c/*新闻类别，新闻自带，回传*/,
                                                 @Query("source") String source/*回传字段，新闻自带，回传*/,
                                                 @Query("t") String t/*新闻点击时间，填毫秒时间戳*/,
                                                 @Query("sid") String sid/*请求标识，新闻列表自带，回传*/,
                                                 @Query("scene") String scene/*场景标识，同一个渠道下可用来区分不同的新闻展现场景；流量入口的区分；*/,
                                                 @Query("func") String func/*传 click*/,
                                                 @Query("s") String s/*新闻数据类型*/,
                                                 @Query("act") String act/*操作行为 点击：act=click*/,
                                                 @Query("style") String style
                                                 /*style=1,大图样式
                                                 style=2,双栏大图（拼接格式，目前只有好搜客户端(hs)使用）
                                                 style=3,普通单图
                                                 style=4，无图
                                                 style=5，默认
                                                 style=6,三图模式
                                                 style=7,花椒直播的单图大图模式
                                                 style=8，卡片样式*/
                                                 );

    /**返回数据中的字段，可以支撑统计需要的字段 */
    @POST("http://ad.toutiao.com/lb/api/" + appendix)
    @Headers("Content-Type:application/octet-stream;charset=UTF-8")
    public Call<ResponseBody> requestAd(@Body RequestBody requestBody);
}

