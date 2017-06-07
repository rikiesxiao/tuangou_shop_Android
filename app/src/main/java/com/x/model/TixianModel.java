package com.x.model;

/**
 * Created by x on 2017/6/7.
 */

public class TixianModel {


    /**
     * create_time : 1496712675
     * money : 500
     * status : 待审核
     * reason :
     * f_create_time : 2017-06-06
     */

    private String create_time;
    private double money;
    private String status;
    private String reason;
    private String f_create_time;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getF_create_time() {
        return f_create_time;
    }

    public void setF_create_time(String f_create_time) {
        this.f_create_time = f_create_time;
    }
}
