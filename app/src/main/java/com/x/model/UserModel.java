package com.x.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class UserModel implements Serializable {


    /**
     * id : 27
     * account_name : xg0001
     * account_password : e10adc3949ba59abbe56e057f20f883e
     * sid : 50
     * name : 西工0001号商家
     * icon :
     * status : 1
     */

    private String id;
    private String account_name;
    private String account_password;
    private String sid;
    private String name;
    private String icon;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_password() {
        return account_password;
    }

    public void setAccount_password(String account_password) {
        this.account_password = account_password;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
