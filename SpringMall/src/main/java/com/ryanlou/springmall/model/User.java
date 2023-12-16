package com.ryanlou.springmall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class User {


    private Integer userId;

    //@JsonProperty("e_mail") //客製化 回傳直key
    private String email;

    @JsonIgnore // 回傳給前端會忽略 避免外洩
    private String password;

    private Date createDate;

    private Date lastModifiedData;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifiedData() {
        return lastModifiedData;
    }

    public void setLastModifiedData(Date lastModifiedData) {
        this.lastModifiedData = lastModifiedData;
    }
}
