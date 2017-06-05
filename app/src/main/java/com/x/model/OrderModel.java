package com.x.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class OrderModel implements Serializable{


    /**
     * id : 212
     * order_sn : 2017052706012742
     * create_time : 1495850487
     * pay_status : 2
     * total_price : 750.0000
     * pay_amount : 750.0000
     * order_status : 1
     * is_delete : 0
     * refund_amount : 0.0000
     * account_money : 750.0000
     * refund_status : 0
     * name : 西工0001号商家1号团购
     * number : 1
     * unit_price : 750.0000
     */

    private String id;
    private String order_sn;
    private String create_time;
    private String pay_status;
    private String total_price;
    private String pay_amount;
    private String order_status;
    private String is_delete;
    private String refund_amount;
    private String account_money;
    private int refund_status;
    private String name;
    private String number;
    private String unit_price;
    private String icon;
    private int dp_id;

    public int getDp_id() {
        return dp_id;
    }

    public void setDp_id(int dp_id) {
        this.dp_id = dp_id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(String refund_amount) {
        this.refund_amount = refund_amount;
    }

    public String getAccount_money() {
        return account_money;
    }

    public void setAccount_money(String account_money) {
        this.account_money = account_money;
    }

    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }
}
