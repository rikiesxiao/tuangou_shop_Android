package com.x.net;

import com.x.model.CommentModel;
import com.x.model.OrderModel;
import com.x.model.UserModel;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by X on 2016/10/1.
 */

public interface ServicesAPI {

 String APPUrl = "http://tg01.sssvip.net/mapi/";


 //商家登录
 @POST("?ctl=biz_user&act=app_dologin&r_type=1&isapp=true")
 Observable<HttpResult<UserModel>> dologin(
         @Query("account_name") String account_name,
         @Query("account_password") String account_password
 );


 //订单列表
 @POST("?ctl=biz_dealo&act=app_index&r_type=1&isapp=true")
 Observable<HttpResult<List<OrderModel>>> order_list(
         @Query("sid") String sid,
         @Query("page") String page
 );


 //点评列表
 @POST("?ctl=biz_dealr&act=app_dp_list&r_type=1&isapp=true")
 Observable<HttpResult<List<CommentModel>>> dp_list(
         @Query("sid") String sid,
         @Query("page") String page
 );

   @GET("?ctl=city&act=city_change&r_type=1&isapp=true")
    Observable<HttpResult<Object>> city_city_change(@Query("city_id") String city_id);


 //获取团购券码
 @POST("?ctl=uc_order&act=app_do_refund_coupon&r_type=1&isapp=true")
 Observable<HttpResult<Object>> uc_do_refund(
         @Query("uid") String uid,
         @Query("content") String content,
         @Query("item_id") String item_id
 );


}


