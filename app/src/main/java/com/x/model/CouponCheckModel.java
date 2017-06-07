package com.x.model;

/**
 * Created by x on 2017/6/7.
 */

public class CouponCheckModel {


    /**
     * biz_user_status : 1
     * is_auth : 1
     * location_id : 57
     * coupon_pwd : 30343365
     * count : 2
     * info : aaa0000 二号团购0001, 消费券有效 一共：2张有效
     */

    private int biz_user_status;
    private int is_auth;
    private int location_id;
    private String coupon_pwd;
    private int count;
    private String info;

    public int getBiz_user_status() {
        return biz_user_status;
    }

    public void setBiz_user_status(int biz_user_status) {
        this.biz_user_status = biz_user_status;
    }

    public int getIs_auth() {
        return is_auth;
    }

    public void setIs_auth(int is_auth) {
        this.is_auth = is_auth;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getCoupon_pwd() {
        return coupon_pwd;
    }

    public void setCoupon_pwd(String coupon_pwd) {
        this.coupon_pwd = coupon_pwd;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
