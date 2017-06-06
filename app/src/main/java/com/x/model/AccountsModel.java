package com.x.model;

/**
 * Created by x on 2017/6/6.
 */

public class AccountsModel {

    /**
     * money : 796.0000
     * bili : 0.1500
     * lock_money : 3192.0000
     * sale_money : 4786.0000
     * refund_money : 798.0000
     * wd_money : 0.0000
     */

    private double money;
    private double bili;
    private double lock_money;
    private double sale_money;
    private double refund_money;
    private double wd_money;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getBili() {
        return bili;
    }

    public void setBili(double bili) {
        this.bili = bili;
    }

    public double getLock_money() {
        return lock_money;
    }

    public void setLock_money(double lock_money) {
        this.lock_money = lock_money;
    }

    public double getSale_money() {
        return sale_money;
    }

    public void setSale_money(double sale_money) {
        this.sale_money = sale_money;
    }

    public double getRefund_money() {
        return refund_money;
    }

    public void setRefund_money(double refund_money) {
        this.refund_money = refund_money;
    }

    public double getWd_money() {
        return wd_money;
    }

    public void setWd_money(double wd_money) {
        this.wd_money = wd_money;
    }
}
