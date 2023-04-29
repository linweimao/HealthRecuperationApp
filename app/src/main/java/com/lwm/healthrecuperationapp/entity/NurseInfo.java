package com.lwm.healthrecuperationapp.entity;

import cn.bmob.v3.BmobObject;

public class NurseInfo extends BmobObject {

    private String username; // 姓名
    private String sex; // 性别
    private Integer age; // 年龄
    private String mobile; // 手机号码
    private String idcard; // 身份证号
    private String address; // 地址
    private String nursingtype; // 护理类型
    private Integer nursingyears; // 护理年数

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNursingtype() {
        return nursingtype;
    }

    public void setNursingtype(String nursingtype) {
        this.nursingtype = nursingtype;
    }

    public Integer getNursingyears() {
        return nursingyears;
    }

    public void setNursingyears(Integer nursingyears) {
        this.nursingyears = nursingyears;
    }

    @Override
    public String toString() {
        return "NurseInfo{" +
                "username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", mobile='" + mobile + '\'' +
                ", idcard='" + idcard + '\'' +
                ", address='" + address + '\'' +
                ", nursingtype='" + nursingtype + '\'' +
                ", nursingyears=" + nursingyears +
                '}';
    }
}
