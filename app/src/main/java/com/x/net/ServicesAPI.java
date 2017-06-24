package com.x.net;

import com.x.model.AccountsModel;
import com.x.model.BankModel;
import com.x.model.CheckHistoryModel;
import com.x.model.CommentModel;
import com.x.model.CouponCheckModel;
import com.x.model.MoneyInfoModel;
import com.x.model.OrderModel;
import com.x.model.ShopsModel;
import com.x.model.TixianModel;
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

 String APPUrl = "http://www.tcbjpt.com/mapi/";


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

 //提交点评
 @POST("?ctl=biz_dealr&act=app_do_reply_dp&r_type=1&isapp=true")
 Observable<HttpResult<Object>> dp_submit(
         @Query("sid") String sid,
         @Query("aid") String aid,
         @Query("data_id") String data_id,
         @Query("reply_content") String reply_content
 );


 //商家账户金额统计
 @POST("?ctl=biz_balance&r_type=1&isapp=true")
 Observable<HttpResult<AccountsModel>> accounts_count(
         @Query("sid") String sid
 );

 //商家账户金额明细列表
 @POST("?ctl=biz_balance&act=detail&r_type=1&isapp=true")
 Observable<HttpResult<List<MoneyInfoModel>>> accounts_list(
         @Query("sid") String sid,
         @Query("page") String page
 );

 //商家银行账户详情
 @POST("?ctl=biz_user&act=bank_info&r_type=1&isapp=true")
 Observable<HttpResult<BankModel>> bank_info(
         @Query("sid") String sid
 );


 //银行账户提交
 @POST("?ctl=biz_user&act=do_save_bank&r_type=1&isapp=true")
 Observable<HttpResult<Object>> do_save_bank(
         @Query("sid") String sid,
         @Query("bank_name") String bank_name,
         @Query("bank_info") String bank_info,
         @Query("bank_user") String bank_user,
         @Query("tel") String tel,
         @Query("code") String code
 );

 //商户提现提交
 @POST("?ctl=biz_withdrawal&act=app_do_submit&r_type=1&isapp=true")
 Observable<HttpResult<Object>> do_tixian_submit(
         @Query("sid") String sid,
         @Query("money") String money,
         @Query("sms_verify") String sms_verify
 );


 //商户提现记录
 @POST("?ctl=biz_withdrawal&act=app_index&r_type=1&isapp=true")
 Observable<HttpResult<List<TixianModel>>> tixian_list(
         @Query("sid") String sid,
         @Query("page") String page
 );

 //用户初始化
 @POST("?ctl=biz_user&act=app_init_user&r_type=1&isapp=true")
 Observable<HttpResult<String>> user_init(
         @Query("id") String id,
         @Query("sid") String sid,
         @Query("a") String a
 );

 //会员登出
 @POST("?ctl=biz_user&act=loginout&r_type=1&isapp=true")
 Observable<HttpResult<String>> user_logout();

 //获取商家门店
 @POST("?ctl=biz_dealv&act=app_index&r_type=1&isapp=true")
 Observable<HttpResult<List<ShopsModel>>> shops_list();

 //验证团购券
 @POST("?ctl=biz_dealv&act=app_check_coupon&r_type=1&isapp=true")
 Observable<HttpResult<CouponCheckModel>> check_coupon(
         @Query("location_id") String location_id,
         @Query("coupon_pwd") String coupon_pwd
 );

 //使用团购券
 @POST("?ctl=biz_dealv&act=use_coupon&r_type=1&isapp=true")
 Observable<HttpResult<CouponCheckModel>> use_coupon(
         @Query("location_id") String location_id,
         @Query("coupon_pwd") String coupon_pwd,
         @Query("coupon_use_count") String coupon_use_count
 );


 //使用团购券
 @POST("?ctl=biz_dealv&act=app_used_history&r_type=1&isapp=true")
 Observable<HttpResult<List<CheckHistoryModel>>> used_history(
         @Query("sid") String sid,
         @Query("aid") String aid,
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

 //发送验证码
 @GET("?ctl=sms&act=send_sms_code&r_type=1&isapp=true&unique=0")
 Observable<HttpResult<Object>> sms_send_code(@Query("mobile") String mobile);


}


